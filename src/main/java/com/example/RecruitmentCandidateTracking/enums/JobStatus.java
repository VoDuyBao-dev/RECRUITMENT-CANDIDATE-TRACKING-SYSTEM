package com.example.RecruitmentCandidateTracking.enums;

public enum JobStatus {
    OPEN, CLOSED, PAUSED    
}

// paused có thể là đã có người được tuyển nên dù chưa hết thời gian ứng tuyển nên 
// công việc vẫn hiển thị nhưng không nhận ứng viên mới,... hoặc một lí do khác 


// close thì khi nào hết thời gian ứng tuyến thì tự động chuyển sang trạng thái đóng