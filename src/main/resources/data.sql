
INSERT INTO semester (name) VALUES
                                ('1-1'),
                                ('1-2'),
                                ('2-1'),
                                ('2-2'),
                                ('3-1'),
                                ('3-2');

INSERT INTO subject (id, name, subject_code, credits,semester_id) VALUES
                                                                           (1, 'Mathematics I',        'MATH101', 4,1),
                                                                           (2, 'Physics',              'PHY102',  3,1),
                                                                           (3, 'Chemistry',            'CHEM103', 3,1),
                                                                           (4, 'Data Structures',      'CS201',   4,2),
                                                                           (5, 'Database Systems',     'CS301',   4,2),
                                                                           (6, 'Operating Systems',    'CS302',   4,3),
                                                                           (7, 'Computer Networks',    'CS303',   3,4),
                                                                           (8, 'Software Engineering', 'CS304',   3,5),
                                                                           (9, 'English Communication','ENG101',  2,6),
                                                                           (10,'Machine Learning',     'CS401',   4,6);

INSERT INTO teacher (name, subject_id) VALUES
                                               ( 'Mr. Sharma', 1),
                                               ( 'Ms. Rao', 2),
                                               ( 'Dr. Mehta', 3),
                                               ( 'Mr. Iyer', 4);


INSERT INTO section (name,teacher_id) VALUES
                                   ('CSE-2A',1),
                                   ('CSE-2B',2),
                                    ( 'CSE-2C',3),
                                   ( 'CSE-2D',4);


    INSERT INTO student (roll_no, name, section_id) VALUES
    ('22CS001', 'Rahul Sharma', 1),
    ('22CS002', 'Priya Reddy', 2),
    ('22CS003', 'Arjun Kumar', 2),
    ('22CS004', 'Sneha Patel', 2),
    ('22CS005', 'Vikram Singh', 2),
    ('22CS006', 'Ananya Gupta', 2),
    ('22CS007', 'Rohan Verma', 2),
    ('22CS008', 'Kavya Nair', 2),
    ('22CS009', 'Aditya Joshi', 2),
    ('22CS010', 'Meera Iyer', 2),

    ('22CS011', 'Karan Mehta', 2),
    ('22CS012', 'Pooja Sharma', 2),
    ('22CS013', 'Nikhil Reddy', 2),
    ('22CS014', 'Divya Kapoor', 2),
    ('22CS015', 'Sandeep Yadav', 2),
    ('22CS016', 'Neha Singh', 2),
    ('22CS017', 'Ritika Patel', 2),
    ('22CS018', 'Manish Gupta', 2),
    ('22CS019', 'Amit Verma', 2),
    ('22CS020', 'Shreya Nair', 2),

    ('22CS021', 'Harsha Reddy', 3),
    ('22CS022', 'Deepak Kumar', 3),
    ('22CS023', 'Anjali Sharma', 3),
    ('22CS024', 'Siddharth Jain', 3),
    ('22CS025', 'Pavan Singh', 3),
    ('22CS026', 'Lakshmi Iyer', 3),
    ('22CS027', 'Tarun Gupta', 3),
    ('22CS028', 'Bhavana Patel', 3),
    ('22CS029', 'Ajay Verma', 3),
    ('22CS030', 'Swathi Nair', 3),

    ('22CS031', 'Gaurav Sharma', 4),
    ('22CS032', 'Keerthi Reddy', 4),
    ('22CS033', 'Rakesh Kumar', 4),
    ('22CS034', 'Isha Patel', 4),
    ('22CS035', 'Abhishek Singh', 4),
    ('22CS036', 'Nisha Gupta', 4),
    ('22CS037', 'Varun Mehta', 4),
    ('22CS038', 'Sahana Iyer', 4),
    ('22CS039', 'Akash Verma', 4),
    ('22CS040', 'Pallavi Nair', 4);


INSERT INTO users (username, password, email, student_id,roles,teacher_id)
VALUES
    ('rahul', '$2a$10$gcM05FFyjHvkurlmbW9ske.716xFixMFPLZUPHIPw5CuEQy81us3.','godasrikar@gmail.com', 1,'STUDENT',null),
    ('srikar', '$2a$10$gcM05FFyjHvkurlmbW9ske.716xFixMFPLZUPHIPw5CuEQy81us3.', 'godanilesh59@gmail.com',null,'TEACHER',1),
    ('srikar345', '$2a$10$gcM05FFyjHvkurlmbW9ske.716xFixMFPLZUPHIPw5CuEQy81us3.', 'srikargoda@gmail.com',4,'STUDENT',null);




INSERT INTO student_subject
(student_id, subject_id, teacher_id, conducted_at, attendance_status, remarks)
VALUES
    (1,1,1,'2026-02-01','PRESENT','On time'),
    (2,1,1,'2026-02-01','PRESENT','Active'),
    (3,1,1,'2026-02-01','ABSENT','Sick'),
    (4,1,1,'2026-02-01','PRESENT','Participated'),
    (5,1,1,'2026-02-01','PRESENT','On time'),
    (6,1,1,'2026-02-01','ABSENT','Medical leave'),
    (7,1,1,'2026-02-01','PRESENT','On time'),
    (8,1,1,'2026-02-01','PRESENT','Active'),
    (9,1,1,'2026-02-01','ABSENT','Late'),
    (10,1,1,'2026-02-02','PRESENT','Participated'),
    (10,1,1,'2026-02-03','PRESENT','Participated'),
    (10,1,1,'2026-02-04','ABSENT','Participated'),
    (10,1,1,'2026-02-05','PRESENT','Participated'),

    (11,2,2,'2026-02-02','PRESENT','On time'),
    (12,2,2,'2026-02-02','ABSENT','Sick'),
    (13,2,2,'2026-02-02','PRESENT','Active'),
    (14,2,2,'2026-02-02','PRESENT','Participated'),
    (15,2,2,'2026-02-02','ABSENT','Medical leave'),
    (16,2,2,'2026-02-02','PRESENT','On time'),
    (17,2,2,'2026-02-02','PRESENT','Active'),
    (18,2,2,'2026-02-02','ABSENT','Late'),
    (19,2,2,'2026-02-02','PRESENT','Participated'),
    (20,2,2,'2026-02-02','PRESENT','On time'),

    (21,3,3,'2026-02-03','PRESENT','On time'),
    (22,3,3,'2026-02-03','PRESENT','Active'),
    (23,3,3,'2026-02-03','ABSENT','Sick'),
    (24,3,3,'2026-02-03','PRESENT','Participated'),
    (25,3,3,'2026-02-03','PRESENT','On time'),
    (26,3,3,'2026-02-03','ABSENT','Medical leave'),
    (27,3,3,'2026-02-03','PRESENT','Active'),
    (28,3,3,'2026-02-03','PRESENT','On time'),
    (29,3,3,'2026-02-03','ABSENT','Late'),
    (30,3,3,'2026-02-03','PRESENT','Participated'),

    (31,4,4,'2026-02-04','PRESENT','On time'),
    (32,4,4,'2026-02-04','PRESENT','Active'),
    (33,4,4,'2026-02-04','ABSENT','Sick'),
    (34,4,4,'2026-02-04','PRESENT','Participated'),
    (35,4,4,'2026-02-04','PRESENT','On time'),
    (36,4,4,'2026-02-04','ABSENT','Medical leave'),
    (37,4,4,'2026-02-04','PRESENT','Active'),
    (38,4,4,'2026-02-04','PRESENT','On time'),
    (39,4,4,'2026-02-04','ABSENT','Late'),
    (40,4,4,'2026-02-04','PRESENT','Participated'),

    (1,2,2,'2026-02-05','PRESENT','On time'),
    (2,2,2,'2026-02-05','ABSENT','Sick'),
    (3,2,2,'2026-02-05','PRESENT','Active'),
    (4,2,2,'2026-02-05','PRESENT','Participated'),
    (5,2,2,'2026-02-05','PRESENT','On time'),
    (6,2,2,'2026-02-05','ABSENT','Medical leave'),
    (7,2,2,'2026-02-05','PRESENT','Active'),
    (8,2,2,'2026-02-05','PRESENT','On time'),
    (9,2,2,'2026-02-05','ABSENT','Late'),
    (10,2,2,'2026-02-05','PRESENT','Participated'),

    (11,3,3,'2026-02-06','PRESENT','On time'),
    (12,3,3,'2026-02-06','PRESENT','Active'),
    (13,3,3,'2026-02-06','ABSENT','Sick'),
    (14,3,3,'2026-02-06','PRESENT','Participated'),
    (15,3,3,'2026-02-06','PRESENT','On time'),
    (16,3,3,'2026-02-06','ABSENT','Medical leave'),
    (17,3,3,'2026-02-06','PRESENT','Active'),
    (18,3,3,'2026-02-06','PRESENT','On time'),
    (19,3,3,'2026-02-06','ABSENT','Late'),
    (20,3,3,'2026-02-06','PRESENT','Participated'),

    (21,4,4,'2026-02-07','PRESENT','On time'),
    (22,4,4,'2026-02-07','PRESENT','Active'),
    (23,4,4,'2026-02-07','ABSENT','Sick'),
    (24,4,4,'2026-02-07','PRESENT','Participated'),
    (25,4,4,'2026-02-07','PRESENT','On time'),
    (26,4,4,'2026-02-07','ABSENT','Medical leave'),
    (27,4,4,'2026-02-07','PRESENT','Active'),
    (28,4,4,'2026-02-07','PRESENT','On time'),
    (29,4,4,'2026-02-07','ABSENT','Late'),
    (30,4,4,'2026-02-07','PRESENT','Participated'),

    (31,1,1,'2026-02-08','PRESENT','On time'),
    (32,1,1,'2026-02-08','PRESENT','Active'),
    (33,1,1,'2026-02-08','ABSENT','Sick'),
    (34,1,1,'2026-02-08','PRESENT','Participated'),
    (35,1,1,'2026-02-08','PRESENT','On time'),
    (36,1,1,'2026-02-08','ABSENT','Medical leave'),
    (37,1,1,'2026-02-08','PRESENT','Active'),
    (38,1,1,'2026-02-08','PRESENT','On time'),
    (39,1,1,'2026-02-08','ABSENT','Late'),
    (40,1,1,'2026-02-08','PRESENT','Participated');


INSERT INTO time_table
(day_of_week, start_time, end_time, location, subject_id, teacher_id, section_id)
VALUES

-- SECTION 1
('MONDAY','09:00:00','10:00:00','A101',1,1,1),
('MONDAY','10:00:00','11:00:00','A102',2,2,1),
('MONDAY','11:00:00','12:00:00','A103',3,3,1),

('TUESDAY','09:00:00','10:00:00','A101',2,2,1),
('TUESDAY','10:00:00','11:00:00','A102',3,3,1),
('TUESDAY','11:00:00','12:00:00','A103',4,4,1),

('WEDNESDAY','09:00:00','10:00:00','A101',3,3,1),
('WEDNESDAY','10:00:00','11:00:00','A102',4,4,1),
('WEDNESDAY','11:00:00','12:00:00','A103',1,1,1),

('THURSDAY','09:00:00','10:00:00','A101',4,4,1),
('THURSDAY','10:00:00','11:00:00','A102',1,1,1),
('THURSDAY','11:00:00','12:00:00','A103',2,2,1),

('FRIDAY','09:00:00','10:00:00','A101',1,1,1),
('FRIDAY','10:00:00','11:00:00','A102',3,3,1),
('FRIDAY','11:00:00','12:00:00','A103',4,4,1),

('SATURDAY','09:00:00','10:00:00','A101',2,2,1),
('SATURDAY','10:00:00','11:00:00','A102',1,1,1),
('SATURDAY','11:00:00','12:00:00','A103',3,3,1),

('SUNDAY','09:00:00','10:00:00','A101',4,4,1),
('SUNDAY','10:00:00','11:00:00','A102',2,2,1),
('SUNDAY','11:00:00','12:00:00','A103',1,1,1),


-- SECTION 2
('MONDAY','09:00:00','10:00:00','B101',2,2,2),
('MONDAY','10:00:00','11:00:00','B102',3,3,2),
('MONDAY','11:00:00','12:00:00','B103',4,4,2),

('TUESDAY','09:00:00','10:00:00','B101',3,3,2),
('TUESDAY','10:00:00','11:00:00','B102',4,4,2),
('TUESDAY','11:00:00','12:00:00','B103',1,1,2),

('WEDNESDAY','09:00:00','10:00:00','B101',4,4,2),
('WEDNESDAY','10:00:00','11:00:00','B102',1,1,2),
('WEDNESDAY','11:00:00','12:00:00','B103',2,2,2),

('THURSDAY','09:00:00','10:00:00','B101',1,1,2),
('THURSDAY','10:00:00','11:00:00','B102',2,2,2),
('THURSDAY','11:00:00','12:00:00','B103',3,3,2),

('FRIDAY','09:00:00','10:00:00','B101',2,2,2),
('FRIDAY','10:00:00','11:00:00','B102',4,4,2),
('FRIDAY','11:00:00','12:00:00','B103',1,1,2),

('SATURDAY','09:00:00','10:00:00','B101',3,3,2),
('SATURDAY','10:00:00','11:00:00','B102',2,2,2),
('SATURDAY','11:00:00','12:00:00','B103',4,4,2),

('SUNDAY','09:00:00','10:00:00','B101',1,1,2),
('SUNDAY','10:00:00','11:00:00','B102',3,3,2),
('SUNDAY','11:00:00','12:00:00','B103',2,2,2),


-- SECTION 3
('MONDAY','09:00:00','10:00:00','C101',3,3,3),
('MONDAY','10:00:00','11:00:00','C102',4,4,3),
('MONDAY','11:00:00','12:00:00','C103',1,1,3),

('TUESDAY','09:00:00','10:00:00','C101',4,4,3),
('TUESDAY','10:00:00','11:00:00','C102',1,1,3),
('TUESDAY','11:00:00','12:00:00','C103',2,2,3),

('WEDNESDAY','09:00:00','10:00:00','C101',1,1,3),
('WEDNESDAY','10:00:00','11:00:00','C102',2,2,3),
('WEDNESDAY','11:00:00','12:00:00','C103',3,3,3),

('THURSDAY','09:00:00','10:00:00','C101',2,2,3),
('THURSDAY','10:00:00','11:00:00','C102',3,3,3),
('THURSDAY','11:00:00','12:00:00','C103',4,4,3),

('FRIDAY','09:00:00','10:00:00','C101',3,3,3),
('FRIDAY','10:00:00','11:00:00','C102',1,1,3),
('FRIDAY','11:00:00','12:00:00','C103',4,4,3),

('SATURDAY','09:00:00','10:00:00','C101',2,2,3),
('SATURDAY','10:00:00','11:00:00','C102',4,4,3),
('SATURDAY','11:00:00','12:00:00','C103',1,1,3),

('SUNDAY','09:00:00','10:00:00','C101',3,3,3),
('SUNDAY','10:00:00','11:00:00','C102',2,2,3),
('SUNDAY','11:00:00','12:00:00','C103',4,4,3),


-- SECTION 4
('MONDAY','09:00:00','10:00:00','D101',4,4,4),
('MONDAY','10:00:00','11:00:00','D102',1,1,4),
('MONDAY','11:00:00','12:00:00','D103',2,2,4),

('TUESDAY','09:00:00','10:00:00','D101',1,1,4),
('TUESDAY','10:00:00','11:00:00','D102',2,2,4),
('TUESDAY','11:00:00','12:00:00','D103',3,3,4),

('WEDNESDAY','09:00:00','10:00:00','D101',2,2,4),
('WEDNESDAY','10:00:00','11:00:00','D102',3,3,4),
('WEDNESDAY','11:00:00','12:00:00','D103',4,4,4),

('THURSDAY','09:00:00','10:00:00','D101',3,3,4),
('THURSDAY','10:00:00','11:00:00','D102',4,4,4),
('THURSDAY','11:00:00','12:00:00','D103',1,1,4),

('FRIDAY','09:00:00','10:00:00','D101',4,4,4),
('FRIDAY','10:00:00','11:00:00','D102',2,2,4),
('FRIDAY','11:00:00','12:00:00','D103',3,3,4),

('SATURDAY','09:00:00','10:00:00','D101',1,1,4),
('SATURDAY','10:00:00','11:00:00','D102',3,3,4),
('SATURDAY','11:00:00','12:00:00','D103',2,2,4),

('SUNDAY','09:00:00','10:00:00','D101',4,4,4),
('SUNDAY','10:00:00','11:00:00','D102',1,1,4),
('SUNDAY','11:00:00','12:00:00','D103',3,3,4);

INSERT INTO notification
(title, message, created_at, teacher_id)
VALUES
    ('Attendance Warning',
     'Low attendance in Mathematics',
     '2026-02-20 10:30:00',
     1),

    ('Class Rescheduled',
     'Physics class moved to 2 PM',
     '2026-02-20 08:00:00',
     2),

    ('Assignment Reminder',
     'Submit the lab report by tomorrow',
     '2026-02-21 09:15:00',
     3),

    ('Exam Announcement',
     'Midterm exam scheduled next Monday',
     '2026-02-21 11:00:00',
     4),

    ('Attendance Reminder',
     'Please mark attendance before leaving class',
     '2026-02-22 08:45:00',
     1),

    ('Holiday Notice',
     'College will remain closed on Friday',
     '2026-02-22 12:00:00',
     2),

    ('Lab Session Update',
     'Chemistry lab moved to Lab 3',
     '2026-02-23 10:00:00',
     3),

    ('Extra Class',
     'Extra session for Calculus this Saturday',
     '2026-02-23 14:30:00',
     4),

    ('Project Submission',
     'Final year project proposal due this week',
     '2026-02-24 09:00:00',
     1),

    ('Attendance Alert',
     'Students below 75% attendance must meet the teacher',
     '2026-02-24 11:20:00',
     2),

    ('Quiz Announcement',
     'Short quiz will be conducted tomorrow',
     '2026-02-25 10:10:00',
     3),

    ('Lecture Cancelled',
     'Today’s lecture is cancelled due to meeting',
     '2026-02-25 07:45:00',
     4),

    ('Workshop Notice',
     'AI workshop registration open',
     '2026-02-26 13:00:00',
     1),

    ('Schedule Update',
     'Timetable updated for next week',
     '2026-02-26 16:10:00',
     2),

    ('Library Reminder',
     'Return borrowed books before due date',
     '2026-02-27 09:30:00',
     3),

    ('Attendance Update',
     'Attendance records have been updated',
     '2026-02-27 12:15:00',
     4),

    ('Seminar Announcement',
     'Guest lecture on Cybersecurity tomorrow',
     '2026-02-28 10:00:00',
     1),

    ('Exam Reminder',
     'Carry ID card for the exam',
     '2026-02-28 08:40:00',
     2),

    ('Project Discussion',
     'Project group meeting after class',
     '2026-03-01 14:00:00',
     3),

    ('System Maintenance',
     'Portal will be down for maintenance tonight',
     '2026-03-01 18:30:00',
     4);

INSERT INTO leave
(reason, remarks, start_date, end_date, created_at,
 status, leave_reason, student_id, teacher_id)
VALUES

    ('High fever and viral infection',
     'Approved. Submit medical certificate.',
     '2026-03-10','2026-03-12','2026-03-09 08:30:00',
     'APPROVED','MEDICAL',1,1),

    ('Preparing for competitive exam',
     'Leave approved for academic preparation',
     '2026-03-13','2026-03-14','2026-03-12 09:15:00',
     'APPROVED','ACADEMIC',2,2),

    ('Personal family work',
     'Pending approval',
     '2026-03-15','2026-03-16','2026-03-14 10:10:00',
     'PENDING','PERSONAL',3,3),

    ('Migraine headache',
     'Medical certificate required',
     '2026-03-17','2026-03-18','2026-03-16 08:40:00',
     'APPROVED','MEDICAL',4,4),

    ('Attending workshop',
     'Pending review',
     '2026-03-19','2026-03-20','2026-03-18 11:00:00',
     'PENDING','ACADEMIC',5,1),

    ('Family function',
     'Rejected due to exams',
     '2026-03-21','2026-03-22','2026-03-20 12:20:00',
     'REJECTED','PERSONAL',6,2),

    ('Severe cold and fever',
     'Approved medical leave',
     '2026-03-23','2026-03-24','2026-03-22 09:00:00',
     'APPROVED','MEDICAL',7,3),

    ('Preparing seminar presentation',
     'Approved academic leave',
     '2026-03-25','2026-03-25','2026-03-24 10:15:00',
     'APPROVED','ACADEMIC',8,4),

    ('Personal family issue',
     'Pending approval',
     '2026-03-26','2026-03-27','2026-03-25 13:10:00',
     'PENDING','PERSONAL',9,1),

    ('Food poisoning',
     'Approved medical leave',
     '2026-03-28','2026-03-29','2026-03-27 08:50:00',
     'APPROVED','MEDICAL',10,2),

    ('University project work',
     'Approved academic leave',
     '2026-03-30','2026-03-31','2026-03-29 09:40:00',
     'APPROVED','ACADEMIC',11,3),

    ('Personal trip',
     'Rejected',
     '2026-04-01','2026-04-02','2026-03-31 12:30:00',
     'REJECTED','PERSONAL',12,4),

    ('High temperature',
     'Approved medical leave',
     '2026-04-03','2026-04-04','2026-04-02 08:20:00',
     'APPROVED','MEDICAL',13,1),

    ('Academic competition',
     'Approved leave',
     '2026-04-05','2026-04-06','2026-04-04 09:10:00',
     'APPROVED','ACADEMIC',14,2),

    ('Family emergency',
     'Pending review',
     '2026-04-07','2026-04-08','2026-04-06 11:45:00',
     'PENDING','PERSONAL',15,3),

    ('Back pain treatment',
     'Approved medical leave',
     '2026-04-09','2026-04-10','2026-04-08 08:35:00',
     'APPROVED','MEDICAL',16,4),

    ('Research presentation',
     'Approved academic leave',
     '2026-04-11','2026-04-12','2026-04-10 10:20:00',
     'APPROVED','ACADEMIC',17,1),

    ('Personal appointment',
     'Rejected',
     '2026-04-13','2026-04-14','2026-04-12 13:30:00',
     'REJECTED','PERSONAL',18,2),

    ('Severe cold',
     'Approved medical leave',
     '2026-04-15','2026-04-16','2026-04-14 08:25:00',
     'APPROVED','MEDICAL',19,3),

    ('Exam preparation',
     'Approved academic leave',
     '2026-04-17','2026-04-18','2026-04-16 09:50:00',
     'APPROVED','ACADEMIC',20,4),

    ('Family work',
     'Pending approval',
     '2026-04-19','2026-04-20','2026-04-18 11:30:00',
     'PENDING','PERSONAL',21,1),

    ('Eye infection',
     'Approved medical leave',
     '2026-04-21','2026-04-22','2026-04-20 08:40:00',
     'APPROVED','MEDICAL',22,2),

    ('Academic seminar',
     'Approved leave',
     '2026-04-23','2026-04-24','2026-04-22 09:35:00',
     'APPROVED','ACADEMIC',23,3),

    ('Personal travel',
     'Rejected',
     '2026-04-25','2026-04-26','2026-04-24 12:00:00',
     'REJECTED','PERSONAL',24,4),

    ('Viral fever',
     'Approved medical leave',
     '2026-04-27','2026-04-28','2026-04-26 08:55:00',
     'APPROVED','MEDICAL',25,1),

    ('Project development',
     'Approved academic leave',
     '2026-04-29','2026-04-30','2026-04-28 09:25:00',
     'APPROVED','ACADEMIC',26,2),

    ('Family visit',
     'Pending approval',
     '2026-05-01','2026-05-02','2026-04-30 10:45:00',
     'PENDING','PERSONAL',27,3),

    ('Dental surgery',
     'Approved medical leave',
     '2026-05-03','2026-05-04','2026-05-02 08:10:00',
     'APPROVED','MEDICAL',28,4),

    ('Academic workshop',
     'Approved leave',
     '2026-05-05','2026-05-06','2026-05-04 09:30:00',
     'APPROVED','ACADEMIC',29,1),

    ('Personal reason',
     'Rejected',
     '2026-05-07','2026-05-08','2026-05-06 12:15:00',
     'REJECTED','PERSONAL',30,2),

    ('Severe cough',
     'Approved medical leave',
     '2026-05-09','2026-05-10','2026-05-08 08:45:00',
     'APPROVED','MEDICAL',31,3),

    ('Academic conference',
     'Approved leave',
     '2026-05-11','2026-05-12','2026-05-10 09:20:00',
     'APPROVED','ACADEMIC',32,4),

    ('Family responsibility',
     'Pending approval',
     '2026-05-13','2026-05-14','2026-05-12 10:50:00',
     'PENDING','PERSONAL',33,1),

    ('Fever and weakness',
     'Approved medical leave',
     '2026-05-15','2026-05-16','2026-05-14 08:30:00',
     'APPROVED','MEDICAL',34,2),

    ('Academic research work',
     'Approved leave',
     '2026-05-17','2026-05-18','2026-05-16 09:40:00',
     'APPROVED','ACADEMIC',35,3),

    ('Personal travel',
     'Rejected',
     '2026-05-19','2026-05-20','2026-05-18 12:00:00',
     'REJECTED','PERSONAL',36,4),

    ('Cold and fever',
     'Approved medical leave',
     '2026-05-21','2026-05-22','2026-05-20 08:15:00',
     'APPROVED','MEDICAL',37,1),

    ('Academic event participation',
     'Approved leave',
     '2026-05-23','2026-05-24','2026-05-22 09:50:00',
     'APPROVED','ACADEMIC',38,2),

    ('Family personal work',
     'Pending approval',
     '2026-05-25','2026-05-26','2026-05-24 11:00:00',
     'PENDING','PERSONAL',39,3),

    ('Severe headache',
     'Approved medical leave',
     '2026-05-27','2026-05-28','2026-05-26 08:35:00',
     'APPROVED','MEDICAL',40,4);

INSERT INTO grade (student_id, subject_id, grade) VALUES
                                                      (1,1,8.9),(1,2,7.5),(1,3,6.8),(1,4,5.4),
                                                      (2,1,7.9),(2,2,6.4),(2,3,5.8),(2,4,4.6),
                                                      (3,1,8.3),(3,2,7.1),(3,3,6.3),(3,4,5.0),
                                                      (4,1,9.0),(4,2,8.2),(4,3,7.6),(4,4,6.5),
                                                      (5,1,6.9),(5,2,6.2),(5,3,5.7),(5,4,4.3),
                                                      (6,1,8.0),(6,2,7.3),(6,3,6.1),(6,4,5.6),
                                                      (7,1,6.5),(7,2,5.9),(7,3,4.8),(7,4,3.7),
                                                      (8,1,7.1),(8,2,6.2),(8,3,5.6),(8,4,4.5),
                                                      (9,1,8.6),(9,2,7.4),(9,3,6.9),(9,4,5.7),
                                                      (10,1,7.8),(10,2,6.8),(10,3,5.9),(10,4,4.9),

                                                      (11,1,8.1),(11,2,7.0),(11,3,6.2),(11,4,5.1),
                                                      (12,1,7.3),(12,2,6.4),(12,3,5.2),(12,4,4.1),
                                                      (13,1,8.8),(13,2,7.9),(13,3,7.2),(13,4,6.4),
                                                      (14,1,7.6),(14,2,6.7),(14,3,5.8),(14,4,4.6),
                                                      (15,1,6.0),(15,2,5.4),(15,3,4.6),(15,4,3.5),
                                                      (16,1,6.8),(16,2,5.9),(16,3,5.1),(16,4,4.2),
                                                      (17,1,8.5),(17,2,7.6),(17,3,6.7),(17,4,5.8),
                                                      (18,1,7.2),(18,2,6.3),(18,3,5.5),(18,4,4.4),
                                                      (19,1,7.4),(19,2,6.6),(19,3,5.8),(19,4,4.9),
                                                      (20,1,8.7),(20,2,7.8),(20,3,6.9),(20,4,5.9),

                                                      (21,1,6.7),(21,2,5.8),(21,3,4.9),(21,4,3.9),
                                                      (22,1,7.9),(22,2,6.8),(22,3,5.9),(22,4,4.7),
                                                      (23,1,7.0),(23,2,6.1),(23,3,5.2),(23,4,4.1),
                                                      (24,1,8.3),(24,2,7.2),(24,3,6.4),(24,4,5.3),
                                                      (25,1,6.4),(25,2,5.6),(25,3,4.7),(25,4,3.8),
                                                      (26,1,7.6),(26,2,6.8),(26,3,5.9),(26,4,4.8),
                                                      (27,1,8.9),(27,2,7.7),(27,3,6.9),(27,4,5.8),
                                                      (28,1,7.1),(28,2,6.2),(28,3,5.3),(28,4,4.2),
                                                      (29,1,6.9),(29,2,5.8),(29,3,4.8),(29,4,3.9),
                                                      (30,1,8.0),(30,2,7.0),(30,3,6.1),(30,4,5.0),

                                                      (31,1,6.6),(31,2,5.7),(31,3,4.9),(31,4,3.8),
                                                      (32,1,7.8),(32,2,6.9),(32,3,5.8),(32,4,4.7),
                                                      (33,1,8.4),(33,2,7.5),(33,3,6.6),(33,4,5.4),
                                                      (34,1,7.2),(34,2,6.3),(34,3,5.4),(34,4,4.2),
                                                      (35,1,6.3),(35,2,5.5),(35,3,4.7),(35,4,3.6),
                                                      (36,1,7.7),(36,2,6.8),(36,3,5.9),(36,4,4.8),
                                                      (37,1,8.2),(37,2,7.3),(37,3,6.5),(37,4,5.3),
                                                      (38,1,6.8),(38,2,5.9),(38,3,5.0),(38,4,4.1),
                                                      (39,1,7.0),(39,2,6.1),(39,3,5.2),(39,4,4.3),
                                                      (40,1,8.1),(40,2,7.2),(40,3,6.4),(40,4,5.2);

INSERT INTO semester (name) VALUES
                                ('1-1'),
                                ('1-2'),
                                ('2-1'),
                                ('2-2'),
                                ('3-1'),
                                ('3-2'),
                                ('4-1'),
                                ('4-2');
