$(document).ready(function () {
    loadStudentIds();
    loadExamIds();

    loadNextResultId();
});

function loadNextResultId() {
    $.ajax({
        url: "http://localhost:8080/api/v1/results/next-id",
        type: "GET",
        success: function (nextId) {
            $("#result_id").val(nextId);
        },
        error: function (error) {
            console.log("Error:", error);
            alert("Failed to load next result ID.");
        }
    });
}


function loadStudentIds() {
    $.ajax({
        url: "http://localhost:8080/api/v1/user/get",
        type: "GET",
        success: function (users) {
            let studentSelect = $("#student_id");
            studentSelect.empty();
            studentSelect.append('<option value="">Select Student</option>');

            users.forEach(user => {
                if (user.role === 'student' || user.role === 'Student') {
                    studentSelect.append(`<option value="${user.u_id}">${user.u_id}</option>`);
                }
            });
        },
        error: function (error) {
            console.log("Error:", error);
            alert("Failed to load students.");
        }
    });
}

function loadExamIds() {
    $.ajax({
        url: "http://localhost:8080/api/v1/exam/getAllExams",
        type: "GET",
        success: function (exams) {
            console.log(exams);
            let examSelect = $("#exam_id");
            examSelect.empty();
            examSelect.append('<option value="">Select Exam</option>');

            exams.forEach(exam => {
                examSelect.append(`<option value="${exam.id}">${exam.id}</option>`);
            });
        },

        error: function (jqXHR, textStatus, errorThrown) {
            console.log("Error Status:", textStatus);
            console.log("Error Thrown:", errorThrown);
            console.log("Response Text:", jqXHR.responseText);
            alert("Failed to load exams.");
        }
    });
}


function saveResult() {
    let resultData = {
        msg: $("#msg").val(),
        totalMark: $("#total_mark").val(),
        exam: { id: $("#exam_id").val() },
        student: { id: $("#student_id").val() }
    };

    $.ajax({
        url: "http://localhost:8080/api/v1/results/save",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(resultData),
        success: function () {
            alert("Result saved successfully!");
            viewResults();
            clearForm();
        },
        error: function (error) {
            console.log("Error:", error);
            alert("Failed to save result.");
        }
    });
}

function viewResults() {
    $.ajax({
        url: "http://localhost:8080/api/v1/results/get",
        type: "GET",
        success: function (results) {
            let tableBody = $("#resultTableBody");
            tableBody.empty();

            results.forEach(result => {
                tableBody.append(`
                        <tr>
                            <td>${result.id}</td>
                            <td>${result.exam.id}</td>
                            <td>${result.student.id}</td>
                            <td>${result.msg}</td>
                            <td>${result.totalMark}</td>
                        </tr>
                    `);
            });
        },
        error: function (error) {
            console.log("Error:", error);
            alert("Failed to load results.");
        }
    });
}

function deleteResult() {
    let resultId = $("#result_id").val();

    if (resultId === "") {
        alert("Please provide a Result ID to delete.");
        return;
    }

    $.ajax({
        url: `http://localhost:8080/api/v1/results/delete/${resultId}`,
        type: "DELETE",
        success: function () {
            alert("Result deleted successfully!");
            viewResults();
            clearForm();
        },
        error: function (error) {
            console.log("Error:", error);
            alert("Failed to delete result.");
        }
    });
}

function clearForm() {
    $("#result_id").val("");
    $("#exam_id").val("");
    $("#student_id").val("");
    $("#msg").val("");
    $("#total_mark").val("");
}