package com.example.System.Service.Teacher;

import com.example.System.DTO.Teacher.StudentListRequestDTO;
import com.example.System.DTO.Teacher.StudentListResponseDTO;
import com.example.System.DTO.Teacher.TeacherAttendancePageDTO;
import com.example.System.Entity.Student;
import com.example.System.Entity.StudentSubject;
import com.example.System.Entity.Teacher;
import com.example.System.Events.AttendanceEvent;
import com.example.System.Publisher.AttendanceEventPublisher;
import com.example.System.Repository.StudentRepository;
import com.example.System.Repository.StudentSubjectRepository;
import com.example.System.Repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherAttendancePageService {

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final StudentSubjectRepository studentSubjectRepository;
    private final AttendanceEventPublisher  attendanceEventPublisher;

    public TeacherAttendancePageDTO getAttendancePage(Long id, String section, LocalDate date) {

        Teacher teacher = teacherRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Teacher not found!"));

        List<Student> students = studentRepository.findStudentsBySection(section);

        if(!studentSubjectRepository.findByTeacherAndConductedAt(id, date, section).isEmpty()){
            List<StudentListResponseDTO> studentListResponseDTOS = studentSubjectRepository.
                    findByTeacherAndConductedAt(id, date, section).stream()
                    .map(this::mapToStudentListResponse)
                    .toList();

            return  TeacherAttendancePageDTO.builder()
                    .id(id)
                    .studentList(studentListResponseDTOS)
                    .subjectName(teacher.getSubject().getName())
                    .semester(null)
                    .date(LocalDate.now())
                    .build();
        }

        List<StudentListResponseDTO> studentListResponseDTOS = students.stream()
                .map(this::mapToStudentListResponseDTO)
                .toList();


        return  TeacherAttendancePageDTO.builder()
                .id(id)
                .studentList(studentListResponseDTOS)
                .subjectName(teacher.getSubject().getName())
                .semester(null)
                .date(LocalDate.now())
                .build();
    }

    private StudentListResponseDTO mapToStudentListResponse(StudentSubject studentSubject) {
        return StudentListResponseDTO.builder()
                .id(studentSubject.getId())
                .name(studentSubject.getStudent().getName())
                .rollNo(studentSubject.getStudent().getRollNo())
                .attendanceStatus(studentSubject.getAttendanceStatus())
                .remarks(studentSubject.getRemarks())
                .build();
    }

    //email exception not allowing all entries to be added
    @CacheEvict(value = "attendancePage" , allEntries = true)
    public void submitAttendance(Long id, List<StudentListRequestDTO> studentListRequestDTOS) {

        Teacher teacher = teacherRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Teacher not found!"));

        for (StudentListRequestDTO studentListRequestDTO : studentListRequestDTOS) {

            System.out.println(studentListRequestDTO.getRollNo());

            Student student = studentRepository.findByRollNo(studentListRequestDTO.getRollNo()).orElseThrow(
                    () -> new IllegalArgumentException("Student not found!")
            );

            StudentSubject studentSubject = new StudentSubject();
            studentSubject.setTeacher(teacher);
            studentSubject.setAttendanceStatus(studentListRequestDTO.getStatus());
            studentSubject.setStudent(student);
            studentSubject.setConductedAt(LocalDate.now());
            studentSubject.setRemarks(studentListRequestDTO.getRemarks());
            studentSubject.setSubject(teacher.getSubject());

            studentSubjectRepository.save(studentSubject);

            AttendanceEvent event = new AttendanceEvent();
            event.setStudentId(student.getId());
            event.setStudentEmail(student.getUser().getEmail());
            event.setSubjectName(studentSubject.getSubject().getName());
            event.setStatus("PRESENT");

            attendanceEventPublisher.publishAttendanceEvent(event);
        }
    }

    private StudentListResponseDTO mapToStudentListResponseDTO(Student student) {
        return StudentListResponseDTO.builder()
                .id(student.getId())
                .name(student.getName())
                .rollNo(student.getRollNo())
                .build();
    }
}
