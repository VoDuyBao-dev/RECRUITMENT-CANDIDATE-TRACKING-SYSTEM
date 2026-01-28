INSERT INTO roles (role_name) VALUES
('ADMIN'),
('HR'),
('INTERVIEWER'),
('CANDIDATE');

-- mật khẩu 12345678 
INSERT INTO users (created_at, email, enabled, full_name, password_hash, status)
VALUES
(NOW(), 'admin@company.com', b'1', 'System Admin', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'ACTIVE'),
(NOW(), 'hr@company.com', b'1', 'HR Manager', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'ACTIVE'),
(NOW(), 'interviewer@company.com', b'1', 'Tech Interviewer', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'ACTIVE'),
(NOW(), 'candidate1@gmail.com', b'1', 'Nguyen Van A', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'ACTIVE'),
(NOW(), 'candidate2@gmail.com', b'1', 'Tran Thi B', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'ACTIVE'),
(NOW(), 'hr_recruiter@company.com', b'1', 'Le Thi Thu (Recruiter)', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'ACTIVE'),
(NOW(), 'tech_lead@company.com', b'1', 'Pham Minh Tuan (Tech Lead)', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'ACTIVE'),
(NOW(), 'pm@company.com', b'1', 'Vo Thi Sau (Product Manager)', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'ACTIVE'),
(NOW(), 'qa_lead@company.com', b'1', 'Doan Van Hau (QA Lead)', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'ACTIVE'),

--  (Candidates)
(NOW(), 'hoangvanc@gmail.com', b'1', 'Hoang Van C', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'ACTIVE'),
(NOW(), 'ngothid@outlook.com', b'1', 'Ngo Thi D', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'ACTIVE'),
(NOW(), 'dangve@yahoo.com', b'1', 'Dang Van E', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'ACTIVE'),
(NOW(), 'buithif@gmail.com', b'1', 'Bui Thi F', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'ACTIVE'),
(NOW(), 'vuvang@icloud.com', b'1', 'Vu Van G', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'ACTIVE'),
(NOW(), 'lythih@gmail.com', b'1', 'Ly Thi H', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'ACTIVE'),
(NOW(), 'truongvani@gmail.com', b'1', 'Truong Van I', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'ACTIVE'),
(NOW(), 'dinhthik@hotmail.com', b'1', 'Dinh Thi K', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'ACTIVE'),
(NOW(), 'maivanl@gmail.com', b'1', 'Mai Van L', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'ACTIVE'),
(NOW(), 'phanthim@gmail.com', b'1', 'Phan Thi M', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'ACTIVE'),

-- Một user bị khóa để test (INACTIVE)
(NOW(), 'blocked_user@gmail.com', b'0', 'User Bi Khoa', '$2a$10$jVKlCQXGAu1AGMBXaM5iJe90dADLpcbTnFw9IiWiNbmfrUz045fsO', 'INACTIVE');


INSERT INTO user_roles (user_id, role) VALUES
(1, 'ADMIN'),
(2, 'HR'),
(3, 'INTERVIEWER'),
(4, 'CANDIDATE'),
(5, 'CANDIDATE');

INSERT INTO user_roles (user_id, role) VALUES
-- 1. Phân quyền cho nhóm Nhân sự & Phỏng vấn
(6, 'HR'),            -- Le Thi Thu (Recruiter)
(7, 'INTERVIEWER'),   -- Pham Minh Tuan (Tech Lead)
(8, 'INTERVIEWER'),   -- Vo Thi Sau (Product Manager)
(9, 'INTERVIEWER'),   -- Doan Van Hau (QA Lead)

-- 2. Phân quyền cho nhóm Ứng viên mới (Candidates)
(10, 'CANDIDATE'), -- Hoang Van C
(11, 'CANDIDATE'), -- Ngo Thi D
(12, 'CANDIDATE'), -- Dang Van E
(13, 'CANDIDATE'), -- Bui Thi F
(14, 'CANDIDATE'), -- Vu Van G
(15, 'CANDIDATE'), -- Ly Thi H
(16, 'CANDIDATE'), -- Truong Van I
(17, 'CANDIDATE'), -- Dinh Thi K
(18, 'CANDIDATE'), -- Mai Van L
(19, 'CANDIDATE'), -- Phan Thi M
(20, 'CANDIDATE'); -- User Bi Khoa (Vẫn cần role dù bị inactive)



INSERT INTO `job_positions` (`created_at`, `branch_name`,`address`, `basic_salary`, `benefits`, `deadline`, `description`, `quantity`, `requirements`, `start_date`, `status`, `title`, `updated_at`, `created_by_user_id`) VALUES
('2026-01-25 10:00:00.000000','HO_CHI_MINH', 'Tòa nhà FPT, 17 Tôn Đức Thắng, Quận 1, TP. HCM', 25000000.00, 'Bảo hiểm y tế, bảo hiểm xã hội đầy đủ; Thưởng hiệu suất hàng quý; Làm việc hybrid (3 ngày văn phòng, 2 ngày remote); Hỗ trợ đào tạo chứng chỉ AWS miễn phí; Nghỉ phép 18 ngày/năm; Team building hàng quý; Cổ phiếu ESOP cho nhân viên lâu năm.', '2026-02-28', 'Chúng tôi đang tìm kiếm Senior Software Engineer để tham gia phát triển hệ thống backend cho nền tảng fintech hàng đầu Việt Nam. Bạn sẽ thiết kế, phát triển và bảo trì các microservices sử dụng Node.js và MongoDB, đảm bảo scalability cho hàng triệu giao dịch hàng ngày. Cơ hội làm việc với công nghệ hiện đại và đội ngũ tài năng.', 3, '- Tốt nghiệp ĐH CNTT hoặc tương đương, GPA > 7.0;\n- 5+ năm kinh nghiệm phát triển backend với Node.js, Express, NestJS;\n- Thành thạo MongoDB, Redis, Docker, Kubernetes;\n- Kinh nghiệm CI/CD với Jenkins hoặc GitHub Actions;\n- Kỹ năng giải quyết vấn đề phức tạp, làm việc nhóm tốt;\n- Tiếng Anh giao tiếp tốt (ưu tiên).', '2026-03-01', 'OPEN', 'Senior Software Engineer (Backend)', '2026-01-25', 1),
('2026-01-24 14:30:00.000000','HO_CHI_MINH', 'Lầu 5, Viettel Complex, 285 Cách Mạng Tháng Tám, Quận 10, TP. HCM', 18000000.00, 'Bảo hiểm toàn diện; Thưởng Tết 2-4 tháng lương; Laptop MacBook Pro cung cấp; Khóa học Udemy miễn phí; Gym membership; Du lịch công ty hàng năm; Lương cạnh tranh theo thị trường.', '2026-02-15', 'Tham gia xây dựng giao diện người dùng cho ứng dụng e-commerce sử dụng React.js và TypeScript. Hợp tác với designer và backend team để tạo trải nghiệm mượt mà, responsive trên mobile và desktop. Tối ưu performance và accessibility theo chuẩn WCAG.', 5, '- 3+ năm kinh nghiệm Front-end với React.js, Redux;\n- Thành thạo HTML5, CSS3, Tailwind CSS hoặc SCSS;\n- Kinh nghiệm Next.js, state management;\n- Hiểu biết RESTful API, GraphQL;\n- Portfolio dự án thực tế;\n- Ưu tiên kinh nghiệm mobile-first design.', '2026-02-16', 'OPEN', 'Front-end Developer (React.js)', '2026-01-24', 1),
('2026-01-23 09:15:00.000000','HO_CHI_MINH', 'Công viên phần mềm Quang Trung, Quận 12, TP. HCM', 35000000.00, 'Lương 3x13; Bảo hiểm cao cấp; Làm việc 100% remote; Hỗ trợ di chuyển; Đào tạo AI/ML quốc tế; Cổ phần công ty; Bữa ăn miễn phí.', '2026-03-10', 'Data Scientist sẽ phân tích dữ liệu lớn để xây dựng mô hình machine learning dự đoán hành vi khách hàng cho hệ thống recommendation engine. Sử dụng Python, TensorFlow để xử lý big data từ Hadoop và Spark.', 2, '- 4+ năm kinh nghiệm Data Science;\n- Thành thạo Python, R, SQL;\n- Kinh nghiệm ML frameworks: Scikit-learn, TensorFlow, PyTorch;\n- Big Data tools: Spark, Kafka;\n- Thống kê nâng cao, EDA;\n- Tiếng Anh đọc viết tốt.', '2026-03-15', 'OPEN', 'Data Scientist (ML Engineer)', '2026-01-23', 1),
('2026-01-22 16:45:00.000000','HO_CHI_MINH', 'Tầng 8, tòa nhà VNG, 98 Nguyễn Thị Minh Khai, Quận 1, TP. HCM', 22000000.00, 'Thưởng KPI; Bảo hiểm gia đình; Laptop cao cấp; Flexible hours; Training budget 20tr/năm; Teambuilding quốc tế.', '2026-02-20', 'DevOps Engineer chịu trách nhiệm tự động hóa deployment, quản lý hạ tầng cloud AWS, đảm bảo zero-downtime cho các ứng dụng SaaS. Triển khai CI/CD pipelines với Terraform và Ansible.', 4, '- 3 năm kinh nghiệm DevOps;\n- Thành thạo AWS/GCP, Docker, Kubernetes;\n- IaC: Terraform, Ansible;\n- CI/CD: Jenkins, GitLab CI;\n- Monitoring: Prometheus, Grafana;\n- Scripting Bash/Python.', '2026-02-21', 'OPEN', 'DevOps Engineer (AWS)', '2026-01-22', 1),
('2026-01-25 11:00:00.000000','HO_CHI_MINH', 'Quận 7, TP. HCM', 15000000.00, 'BHXH, BHYT; Thưởng quý; Remote option; Khóa học online; Ăn trưa miễn phí.', '2026-02-10', 'Fullstack Developer fresher tham gia dự án web app nội bộ, học hỏi từ senior để phát triển fullstack với MERN stack.', 6, '- Fresher CNTT;\n- Biết cơ bản JS, React, Node;\n- Ham học hỏi;\n- Làm việc nhóm.', '2026-02-11', 'OPEN', 'Fullstack Developer Fresher', '2026-01-25', 1),
('2026-01-20 08:00:00.000000','HA_NOI', 'Hà Nội (có thể remote)', 28000000.00, 'Full benefits; Stock options; International conference.', '2026-02-05', 'Backend PHP Laravel cho hệ thống ERP doanh nghiệp.', 2, '- 4+ năm PHP/Laravel;\n- MySQL, Redis;\n- API design.', '2026-02-06', 'PAUSED', 'Backend PHP Developer', '2026-01-20', 1),
('2026-01-18 13:20:00.000000','HO_CHI_MINH', 'Quận 1, TP. HCM', 30000000.00, 'High salary; Premium insurance; Remote.', '2026-02-25', 'AI Engineer xây dựng mô hình NLP cho chatbot.', 1, '- Kinh nghiệm Python, NLP;\n- Transformers, BERT.', '2026-03-01', 'OPEN', 'AI/ML Engineer', '2026-01-18', 1),
('2026-01-21 10:30:00.000000','HO_CHI_MINH', 'TP. HCM', 20000000.00, 'Standard benefits; Bonus.', '2026-02-12', 'QA Engineer kiểm thử automation Selenium.', 3, '- 2+ năm QA;\n- Selenium, JUnit;\n- Agile.', '2026-02-13', 'OPEN', 'QA Automation Engineer', '2026-01-21', 1),
('2026-01-19 15:00:00.000000','HO_CHI_MINH', 'Quận Bình Thạnh, TP. HCM', 26000000.00, 'Gym, health check; Bonus.', '2026-02-18', 'Mobile Developer Flutter cho app fintech.', 4, '- Flutter/Dart;\n- iOS/Android native knowledge.', '2026-02-19', 'OPEN', 'Flutter Mobile Developer', '2026-01-19', 1),
('2026-01-24 12:00:00.000000','HO_CHI_MINH', 'TP. HCM', 32000000.00, 'Executive benefits; Car allowance.', '2026-03-05', 'IT Project Manager quản lý dự án outsourcing.', 1, '- PMP certified;\n- 5+ năm PM IT;\n- Agile/Scrum.', '2026-03-06', 'OPEN', 'IT Project Manager', '2026-01-24', 1);
-- BẮT ĐẦU INSERT DỮ LIỆU MẪU --
INSERT INTO `job_positions` 
(created_at,branch_name, address, basic_salary, benefits, deadline, description, quantity, requirements, start_date, status, title, updated_at, created_by_user_id) 
VALUES 
/* 1. SENIOR JAVA BACKEND (Lương cao, yêu cầu khắt khe) */
(NOW(), 'HA_NOI',
'Tầng 18, Keangnam Landmark 72, Phạm Hùng, Hà Nội', 
65000000.00, -- ~2500$
'TẠI SAO BẠN SẼ YÊU THÍCH CÔNG VIỆC NÀY:\n- Lương Net cạnh tranh lên tới $3000 + Thưởng dự án (Project Bonus).\n- Thưởng tháng 13 cam kết + Thưởng hiệu suất (1-3 tháng lương).\n- Cấp MacBook Pro M2 Max và màn hình Dell 4K.\n- Bảo hiểm sức khỏe PVI Premium cho nhân viên và người thân.\n- Review lương định kỳ 2 lần/năm (Tháng 6 & Tháng 12).\n- Du lịch công ty (Company Trip) hàng năm tiêu chuẩn 5 sao.', 
'2026-05-30', 
'MÔ TẢ CÔNG VIỆC:\n- Tham gia vào toàn bộ vòng đời phát triển phần mềm (SDLC) của các hệ thống tài chính ngân hàng (Fintech) quy mô lớn.\n- Thiết kế và phát triển các Microservices sử dụng Java Core, Spring Boot.\n- Tối ưu hóa hiệu năng hệ thống (High Performance), xử lý hàng triệu transaction mỗi ngày.\n- Thiết kế kiến trúc Database (MySQL, PostgreSQL) và tối ưu câu lệnh truy vấn (Query Optimization).\n- Review code, hướng dẫn (mentor) các thành viên Junior/Middle trong team.\n- Phối hợp với team DevOps để triển khai hệ thống lên AWS/Kubernetes.', 
3, 
'YÊU CẦU BẮT BUỘC:\n- Tối thiểu 4 năm kinh nghiệm làm việc chuyên sâu với Java.\n- Thành thạo Spring Ecosystem (Spring Boot, Spring Security, Spring Cloud).\n- Có kinh nghiệm thực tế về Microservices Architecture và RESTful APIs.\n- Hiểu sâu về Database (SQL & NoSQL như Redis, MongoDB, Cassandra).\n- Có kinh nghiệm làm việc với Message Queue (Kafka, RabbitMQ).\n\nĐIỂM CỘNG (NICE TO HAVE):\n- Có kinh nghiệm làm việc với các hệ thống thanh toán (Payment Gateway).\n- Hiểu biết về CI/CD pipelines (Jenkins, GitLab CI).\n- Tiếng Anh giao tiếp tốt.', 
'2026-03-01', 
'OPEN', 
'Senior Java Backend Developer (Up to $3000)', 
CURDATE(), 
1),
/* 2. FRONTEND REACTJS (Focus vào UI/UX) */
(NOW(), 'HO_CHI_MINH',
'Tòa nhà Etown 2, 364 Cộng Hòa, Tân Bình, TP.HCM', 
35000000.00, 
'QUYỀN LỢI ĐƯỢC HƯỞNG:\n- Môi trường làm việc Agile/Scrum năng động, trẻ trung.\n- Gói Welcome Kit trị giá 5.000.000 VNĐ.\n- Teambuilding hàng quý, Happy Hour chiều thứ 6 hàng tuần.\n- Hỗ trợ chi phí thi chứng chỉ quốc tế.\n- 15 ngày phép năm + Nghỉ giáng sinh.', 
'2026-04-15', 
'TRÁCH NHIỆM CHÍNH:\n- Phát triển giao diện người dùng (Front-end) cho ứng dụng E-commerce sử dụng ReactJS.\n- Chuyển đổi thiết kế từ Figma sang code HTML/CSS/JS với độ chính xác cao (Pixel-perfect).\n- Tối ưu hóa tốc độ tải trang (Page Speed) và trải nghiệm người dùng (UX).\n- Đảm bảo ứng dụng hiển thị tốt trên mọi thiết bị (Responsive Design) và trình duyệt (Cross-browser).\n- Tích hợp API từ phía Backend và xử lý logic hiển thị phức tạp.', 
5, 
'YÊU CẦU CHUYÊN MÔN:\n- Ít nhất 2.5 năm kinh nghiệm với ReactJS, Redux (hoặc Context API).\n- Nắm vững HTML5, CSS3, SCSS/SASS và JavaScript hiện đại (ES6+).\n- Có kinh nghiệm sử dụng Next.js để tối ưu SEO là một lợi thế lớn.\n- Hiểu biết về Webpack, Babel, Vite.\n- Có tư duy thẩm mỹ tốt, chú trọng đến từng chi tiết nhỏ trong UI.', 
'2026-02-20', 
'OPEN', 
'Middle Frontend Developer (ReactJS/Next.js)', 
CURDATE(), 
1),
/* 3. SENIOR DEVOPS (Hạ tầng Cloud) */
(NOW(), 'HA_NOI',
'Tòa nhà FPT, 17 Duy Tân, Cầu Giấy, Hà Nội', 
70000000.00, 
'PHÚC LỢI:\n- Chế độ làm việc Hybrid (Lên văn phòng 2 ngày/tuần).\n- Tài trợ 100% chi phí thi chứng chỉ AWS/Azure Professional.\n- Bảo hiểm sức khỏe FPT Care.\n- Thưởng signing bonus 1 tháng lương nếu nhận việc ngay.', 
'2026-06-01', 
'MÔ TẢ CÔNG VIỆC:\n- Xây dựng, quản trị và tối ưu hóa hạ tầng trên nền tảng Cloud (AWS là chủ yếu).\n- Thiết kế và vận hành hệ thống CI/CD Pipelines (Jenkins, GitLab CI/CD) để tự động hóa quy trình deploy.\n- Quản lý Container Orchestration sử dụng Kubernetes (EKS) và Docker.\n- Xây dựng hệ thống giám sát (Monitoring) và cảnh báo (Alerting) dùng Prometheus, Grafana, ELK Stack.\n- Đảm bảo tính bảo mật (Security) và khả năng mở rộng (Scalability) của hệ thống.', 
2, 
'YÊU CẦU:\n- 3+ năm kinh nghiệm ở vị trí DevOps hoặc System Administrator.\n- Kinh nghiệm thực tế (Hands-on) sâu rộng với AWS (EC2, S3, RDS, VPC, IAM...).\n- Thành thạo kỹ năng Scripting (Bash, Python).\n- Kinh nghiệm vận hành Kubernetes và Helm Charts.\n- Hiểu biết về Infrastructure as Code (Terraform, Ansible).', 
'2026-03-15', 
'OPEN', 
'Senior DevOps Engineer (AWS/Kubernetes)', 
CURDATE(), 
1),
/* . BUSINESS ANALYST (Tiếng Anh, Onsite) */
(NOW(), 'HO_CHI_MINH',
'Tòa nhà Mapletree, Nguyễn Văn Linh, Quận 7, TP.HCM', 
32000000.00, 
'CHẾ ĐỘ ĐÃI NGỘ:\n- Cơ hội Onsite ngắn hạn tại Singapore hoặc Nhật Bản.\n- Lớp học tiếng Anh/tiếng Nhật miễn phí tại công ty.\n- CLB Thể thao (Bóng đá, Cầu lông, Yoga) được tài trợ chi phí.\n- Kiểm tra sức khỏe tổng quát định kỳ tại bệnh viện quốc tế.', 
'2026-03-10', 
'MÔ TẢ CÔNG VIỆC:\n- Làm việc trực tiếp với khách hàng nước ngoài để khơi gợi (Elicit) và phân tích yêu cầu nghiệp vụ.\n- Phân tích và xây dựng tài liệu đặc tả yêu cầu (SRS, URD, User Stories).\n- Vẽ các biểu đồ quy trình nghiệp vụ (BPMN, UML, Flowchart).\n- Thiết kế Wireframe/Mockup giao diện sơ bộ để thống nhất với khách hàng.\n- Hỗ trợ team Dev và QC trong việc làm rõ yêu cầu nghiệp vụ (Q&A).\n- Tổ chức buổi UAT (User Acceptance Testing) với khách hàng.', 
4, 
'YÊU CẦU CÔNG VIỆC:\n- Tốt nghiệp Đại học chuyên ngành CNTT hoặc Hệ thống thông tin.\n- Tối thiểu 2 năm kinh nghiệm làm BA cho dự án phần mềm.\n- Tiếng Anh giao tiếp tốt (tương đương IELTS 6.5 trở lên), có khả năng đọc viết tài liệu chuyên ngành.\n- Tư duy logic, phân tích và giải quyết vấn đề tốt.\n- Kỹ năng giao tiếp và thuyết trình tự tin.', 
'2026-02-15', 
'OPEN', 
'IT Business Analyst (Global Projects)', 
CURDATE(), 
1),
/* 5. MOBILE DEVELOPER (Flutter - Đa nền tảng) */
(NOW(), 'HA_NOI',
'Tòa nhà CMC, 11 Duy Tân, Cầu Giấy, Hà Nội', 
42000000.00, 
'QUYỀN LỢI:\n- Lương tháng 13 + Thưởng KPI cuối năm.\n- Hỗ trợ ăn trưa và gửi xe miễn phí.\n- Môi trường làm việc cởi mở, tôn trọng ý kiến cá nhân.\n- Cơ hội tiếp cận các công nghệ mới nhất về AI trên Mobile.', 
'2026-03-30', 
'NHIỆM VỤ:\n- Phát triển ứng dụng Mobile đa nền tảng (iOS & Android) chất lượng cao sử dụng Flutter Framework.\n- Tích hợp các thư viện bên thứ 3 và API (Google Maps, Firebase, Payment Gateway...).\n- Tối ưu hóa hiệu năng ứng dụng (Memory, Battery, Network).\n- Viết Unit Test để đảm bảo chất lượng code.\n- Build và Publish ứng dụng lên App Store và Google Play Store.', 
3, 
'YÊU CẦU:\n- 2+ năm kinh nghiệm phát triển Mobile App (Native hoặc Cross-platform).\n- Thành thạo ngôn ngữ Dart và Flutter Framework.\n- Có kinh nghiệm với các mô hình quản lý trạng thái (State Management) như BLoC, Provider, GetX.\n- Hiểu biết về vòng đời phát triển ứng dụng Mobile.\n- Có ứng dụng demo trên Store là một lợi thế lớn.', 
'2026-02-25', 
'OPEN', 
'Mobile Developer (Flutter/Dart)', 
CURDATE(), 
1),
/* 6. AUTOMATION TESTER (Chuyển đổi số) */
(NOW(), 'HA_NOI',
'Tòa nhà IPH, Xuân Thủy, Cầu Giấy, Hà Nội', 
28000000.00, 
'PHÚC LỢI:\n- Đào tạo chuyển đổi từ Manual sang Automation chuyên sâu.\n- Nghỉ thứ 7, Chủ Nhật.\n- Trợ cấp laptop và thiết bị test (iPhone, Android phones).\n- Tiệc sinh nhật hàng tháng.', 
'2026-02-28', 
'MÔ TẢ CÔNG VIỆC:\n- Phân tích yêu cầu và xây dựng kế hoạch kiểm thử (Test Plan).\n- Viết kịch bản kiểm thử tự động (Test Scripts) cho Web và API.\n- Vận hành và bảo trì hệ thống Automation Test Framework.\n- Thực hiện kiểm thử hiệu năng (Performance Test) và bảo mật (Security Test) cơ bản.\n- Báo cáo lỗi (Bug Report) chi tiết và theo dõi tiến độ fix bug.', 
2, 
'YÊU CẦU:\n- Tốt nghiệp chuyên ngành CNTT.\n- Có ít nhất 1 năm kinh nghiệm làm Automation Testing.\n- Thành thạo Selenium WebDriver hoặc Cypress/Playwright.\n- Có kiến thức lập trình cơ bản (Java/Python/JS) để viết script.\n- Tư duy cẩn thận, tỉ mỉ, "soi" lỗi tốt.', 
'2026-02-15', 
'OPEN', 
'Automation Tester (Selenium/Java)', 
CURDATE(), 
1),
/* 7. FRESHER JAVA (Chương trình tài năng trẻ) */
(NOW(), 'HO_CHI_MINH',
'Khu Công Nghệ Cao, Quận 9, TP.HCM', 
15000000.00, 
'CHƯƠNG TRÌNH ĐÀO TẠO:\n- Được tham gia Bootcamp đào tạo 2 tháng full-time có hưởng lương.\n- Lộ trình thăng tiến lên Junior sau 6 tháng.\n- Được Mentor bởi các Senior/Architect hàng đầu.\n- Tham gia các hoạt động ngoại khóa, thể thao của tập đoàn.', 
'2026-03-15', 
'BẠN SẼ LÀM GÌ:\n- Tham gia vào các dự án thật (Real projects) sau thời gian đào tạo.\n- Tìm hiểu và nghiên cứu các công nghệ mới theo yêu cầu của dự án.\n- Viết Unit Test và tài liệu kỹ thuật.\n- Hỗ trợ fix bug và phát triển các tính năng nhỏ (Small features).', 
15, 
'YÊU CẦU:\n- Sinh viên năm cuối hoặc mới tốt nghiệp chuyên ngành CNTT, ĐTVT (GPA > 2.8/4.0).\n- Nắm vững kiến thức nền tảng: OOP, Cấu trúc dữ liệu & Giải thuật, Cơ sở dữ liệu.\n- Có tư duy lập trình tốt, yêu thích Java.\n- Tiếng Anh đọc hiểu tài liệu kỹ thuật tốt.\n- Thái độ tích cực, ham học hỏi và chịu được áp lực.', 
'2026-03-01', 
'OPEN', 
'Fresher Java Developer (Training Program)', 
CURDATE(), 
1),
/* 8. SOLUTION ARCHITECT (Đã tuyển đủ - Status CLOSED) */
(NOW(), 'HO_CHI_MINH',
'Tòa nhà Landmark 81, Bình Thạnh, TP.HCM', 
120000000.00, 
'PHÚC LỢI ĐẶC BIỆT:\n- Cổ phần ưu đãi (ESOP) sau 1 năm làm việc.\n- Gói bảo hiểm sức khỏe toàn cầu VIP.\n- Xe hơi đưa đón khi đi công tác.\n- Môi trường làm việc đẳng cấp quốc tế.', 
'2025-12-31', 
'VAI TRÒ CHIẾN LƯỢC:\n- Chịu trách nhiệm thiết kế kiến trúc tổng thể (High-level design) cho toàn bộ hệ sinh thái sản phẩm.\n- Ra quyết định lựa chọn công nghệ (Tech Stack) phù hợp với định hướng kinh doanh.\n- Giải quyết các bài toán hóc búa về Hiệu năng, Bảo mật và Khả năng mở rộng.\n- Tư vấn giải pháp kỹ thuật cho Ban giám đốc và Khách hàng lớn.', 
1, 
'YÊU CẦU:\n- Tối thiểu 10 năm kinh nghiệm trong ngành phần mềm, 3 năm tại vị trí Architect.\n- Kiến thức uyên thâm về Cloud Native, Microservices, Big Data.\n- Kỹ năng Leadership và thuyết trình xuất sắc.', 
'2026-01-01', 
'CLOSED', 
'Chief Solution Architect', 
'2026-01-15', 
1),
/* 9. UI/UX DESIGNER (Tạm dừng tuyển - Status PAUSED) */
(NOW(), 'HO_CHI_MINH',
'Co-working Space Toong, Quận 3, TP.HCM', 
28000000.00, 
'CULTURE:\n- Không gian làm việc sáng tạo, nghệ thuật.\n- Thời gian làm việc linh hoạt (Flexible hours).\n- Ăn nhẹ, trà, cà phê miễn phí không giới hạn.', 
'2026-03-20', 
'MÔ TẢ:\n- Thiết kế giao diện Website và Mobile App đảm bảo tính thẩm mỹ và tiện dụng.\n- Nghiên cứu người dùng (User Research), xây dựng User Persona, User Journey Map.\n- Thiết kế hệ thống Design System đồng bộ cho sản phẩm.\n- Tạo Prototype tương tác để test với người dùng.', 
2, 
'YÊU CẦU:\n- Sử dụng thành thạo Figma, Adobe XD, Photoshop, Illustrator.\n- Có Portfolio ấn tượng thể hiện tư duy thiết kế hiện đại (Bắt buộc đính kèm link).\n- Hiểu biết cơ bản về HTML/CSS để trao đổi với Dev.', 
'2026-02-15', 
'PAUSED', 
'UI/UX Designer (Product Design)', 
CURDATE(), 
1),
/* 10. PROJECT MANAGER (Quản trị dự án) */
(NOW(), 'HA_NOI',
'Tòa nhà Lotte Center, Liễu Giai, Hà Nội', 
55000000.00, 
'PHÚC LỢI:\n- Lương tháng 13 + Thưởng lợi nhuận dự án (Project Profit Share).\n- Cơ hội đi công tác nước ngoài thường xuyên (Mỹ, Úc).\n- Chế độ chăm sóc sức khỏe đặc biệt cho quản lý.', 
'2026-05-01', 
'TRÁCH NHIỆM:\n- Lập kế hoạch chi tiết, phân bổ nguồn lực và giám sát tiến độ dự án.\n- Quản lý ngân sách (Budgeting) và rủi ro (Risk Management).\n- Là đầu mối liên lạc chính (Point of Contact) giữa team dự án và khách hàng.\n- Đảm bảo dự án bàn giao đúng hạn (On-time), đúng chất lượng (On-quality) và trong ngân sách (On-budget).', 
2, 
'YÊU CẦU:\n- Có chứng chỉ PMP (Project Management Professional) hoặc Scrum Master là lợi thế lớn.\n- Ít nhất 4 năm kinh nghiệm quản lý dự án phần mềm quy mô trung bình đến lớn.\n- Kỹ năng quản lý con người, giải quyết xung đột và đàm phán xuất sắc.\n- Tiếng Anh lưu loát (Nghe, Nói, Đọc, Viết).', 
'2026-03-10', 
'OPEN', 
'Senior Project Manager (Software)', 
CURDATE(), 
1);

INSERT INTO applications
(applied_date, current_stage, resume_path, candidate_id, job_id)
VALUES
(NOW(), 'SCREENING', '/resumes/candidate1.pdf', 4, 1),
(NOW(), 'INTERVIEWING', '/resumes/candidate2.pdf', 5, 2);
INSERT INTO applications
(applied_date, current_stage, resume_path, candidate_id, job_id)
VALUES
-- Candidate 4 apply Backend Developer
('2026-01-26 09:15:00', 'APPLIED', '/resumes/nguyen_van_a_backend.pdf', 4, 2),

-- Candidate 5 apply Backend Developer (đã qua screening)
('2026-01-26 10:30:00', 'SCREENING', '/resumes/tran_thi_b_backend.pdf', 5, 1),

-- Candidate 6 apply Frontend Developer (đang phỏng vấn)
('2026-01-26 14:00:00', 'INTERVIEWING', '/resumes/le_van_c_frontend.pdf', 4, 4),

-- Candidate 4 apply Fullstack Developer (đã offer)
('2026-01-26 16:45:00', 'OFFERED', '/resumes/nguyen_van_a_fullstack.pdf', 4, 3),

-- Candidate 5 apply Fullstack Developer (đã reject)
('2026-01-26 11:20:00', 'REJECTED', '/resumes/tran_thi_b_fullstack.pdf', 5, 3),

-- Candidate 6 apply AI Engineer (đã hired)
('2026-01-26 09:00:00', 'HIRED', '/resumes/le_van_c_ai.pdf', 4, 6);

INSERT INTO applications
(applied_date, current_stage, resume_path, candidate_id, job_id)
VALUES
-- Hoang Van C (user_id = 10)
('2026-01-26 08:30:00', 'APPLIED', '/resumes/hoang_van_c_senior_backend.pdf', 10, 1),
('2026-01-26 09:00:00', 'SCREENING', '/resumes/hoang_van_c_frontend.pdf', 10, 2),

-- Ngo Thi D (user_id = 11)
('2026-01-26 10:15:00', 'INTERVIEWING', '/resumes/ngo_thi_d_data_science.pdf', 11, 3),
('2026-01-26 14:20:00', 'APPLIED', '/resumes/ngo_thi_d_devops.pdf', 11, 4),

-- Dang Van E (user_id = 12)
('2026-01-26 11:45:00', 'SCREENING', '/resumes/dang_van_e_fullstack.pdf', 12, 5),
('2026-01-26 09:30:00', 'REJECTED', '/resumes/dang_van_e_php_backend.pdf', 12, 6),

-- Bui Thi F (user_id = 13)
('2026-01-26 13:00:00', 'INTERVIEWING', '/resumes/bui_thi_f_ai_engineer.pdf', 13, 7),

-- Vu Van G (user_id = 14)
('2026-01-26 15:10:00', 'APPLIED', '/resumes/vu_van_g_qa.pdf', 14, 8),
('2026-01-26 10:40:00', 'SCREENING', '/resumes/vu_van_g_flutter.pdf', 14, 9),

-- Ly Thi H (user_id = 15)
('2026-01-26 16:00:00', 'SCREENING', '/resumes/ly_thi_h_pm.pdf', 15, 11),

-- Truong Van I (user_id = 16)
('2026-01-26 09:50:00', 'APPLIED', '/resumes/truong_van_i_frontend.pdf', 16, 2),

-- Dinh Thi K (user_id = 17)
('2026-01-26 11:30:00', 'HIRED', '/resumes/dinh_thi_k_data_scientist.pdf', 17, 3),

-- Mai Van L (user_id = 18)
('2026-01-26 14:00:00', 'OFFERED', '/resumes/mai_van_l_devops.pdf', 18, 4),

-- Phan Thi M (user_id = 19)
('2026-01-26 09:10:00', 'REJECTED', '/resumes/phan_thi_m_fullstack.pdf', 19, 5);



INSERT INTO interviews
(end_time, interview_type, round_name, round_number, scheduled_time, application_id)
VALUES
-- Application 1 (SCREENING)
('2026-01-27 10:00:00', 'ONLINE', 'Technical Interview', 1, '2026-01-27 09:00:00', 1),

-- Application 3 (SCREENING)
('2026-01-28 15:30:00', 'OFFLINE', 'HR Interview', 1, '2026-01-28 14:30:00', 3),

-- Application 10 (SCREENING)
('2026-01-29 11:00:00', 'ONLINE', 'Technical Round 1', 1, '2026-01-29 10:00:00', 10),

-- Application 14 (SCREENING)
('2026-01-30 17:00:00', 'OFFLINE', 'Onsite Interview', 1, '2026-01-30 16:00:00', 14);


INSERT INTO interview_interviewers (interview_id, user_id)
VALUES
-- Interview 1
(1, 7),
(1, 8),

-- Interview 2
(2, 6),

-- Interview 3
(3, 7),
(3, 9),

-- Interview 4
(4, 8);



INSERT INTO evaluations
(created_at, comment, score, updated_at, interview_id, interviewer_id)
VALUES
-- Application 2 – Interview 1
('2026-01-27 11:00:00', 'Ứng viên nắm vững backend, trả lời tốt về system design.', 85, '2026-01-27', 1, 7),

-- Application 4 – Interview 2
('2026-01-28 16:00:00', 'Kỹ năng giao tiếp ổn, cần cải thiện kiến thức chuyên sâu.', 78, '2026-01-28', 2, 6),

-- Application 7 – Interview 3
('2026-01-29 12:00:00', 'Kiến thức AI tốt, hiểu rõ mô hình Transformer.', 90, '2026-01-29', 3, 7),

-- Application 13 – Interview 4
('2026-01-30 18:00:00', 'Ứng viên có kinh nghiệm QA automation thực tế.', 82, '2026-01-30', 4, 8);



INSERT INTO offers
(created_at, basic_salary, contract_type, probation_salary,
 start_work_date, application_id, approved_by)
VALUES
(NOW(), 16000000, 'FULL_TIME', 14000000,
 '2026-03-20', 1, 2);


INSERT INTO verification_tokens
(created_at, email, expires_at, token, used)
VALUES
(NOW(), 'candidate1@gmail.com', DATE_ADD(NOW(), INTERVAL 1 DAY), 'token123', b'0');


