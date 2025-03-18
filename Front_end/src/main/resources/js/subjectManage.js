$(document).ready(function () {
    loadUserIds();
    loadNextId();

    $('#dataTable').on('click', 'tr', function () {
        var id = $(this).find('td:eq(0)').text();
        var name = $(this).find('td:eq(1)').text();
        var st_count = $(this).find('td:eq(2)').text();
        var userId = $(this).find('td:eq(3)').text();
        var date = $(this).find('td:eq(4)').text();
        var time = $(this).find('td:eq(5)').text();

        $('#id').val(id);
        $('#name').val(name);
        $('#st_count').val(st_count);
        $('#u_id').val(userId);
        $('#date').val(date);
        $('#time').val(time);
    });

});

function loadNextId() {
    $.ajax({
        url: "http://localhost:8080/api/v1/subject/next-id",
        type: "GET",
        success: function (nextId) {
            $('#id').val(nextId);
        },
        error: function () {
            alert("Error fetching next ID.");
        }
    });
}
function saveData() {
    let userId = $("#u_id").val();

    if (!userId) {
        alert("Please select a User!");
        return;
    }

    const subData = {
        name: $("#name").val(),
        st_count: parseInt($("#st_count").val()),
        userId: parseInt(userId),
        date: $("#date").val(),
        time: $("#time").val()
    };

    console.log("Sending data:", JSON.stringify(subData));

    $.ajax({
        url: "http://localhost:8080/api/v1/subject/save",
        method: "POST",
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify(subData),
        success: function (resp) {
            alert(resp.msg);
            console.log("Success:", resp);
            loadAllSubjects();
            loadNextId();
            $("#userForm")[0].reset();
        },
        error: function (xhr) {
            try {
                let response = JSON.parse(xhr.responseText);
                alert("Error: " + response.message);
                console.log("Error:", response);
            } catch (e) {
                alert("Unexpected error occurred!");
                console.log("Parsing error:", e);
            }
        }
    });
}


function updateData() {
    let id = $("#id").val();
    let subject = {
        id: id,
        name: $("#name").val(),
        st_count: $("#st_count").val(),
        date: $("#date").val(),
        time: $("#time").val(),
    };

    $.ajax({
        url: `http://localhost:8080/api/v1/subject/update/${id}`,
        type: "PUT",
        contentType: "application/json",
        data: JSON.stringify(subject),
        success: function () {
            alert("Subject updated successfully!");
            loadAllSubjects();
            $("#userForm")[0].reset();
        },
        error: function () {
            alert("Error updating Subject.");
        }
    });
}

function loadAllSubjects() {
    $.ajax({
        url: "http://localhost:8080/api/v1/subject/get",
        type: "GET",
        success: function (data) {
            let tableBody = $("#dataTable");
            tableBody.empty();
            data.forEach(subject => {
                let userIdDisplay = subject.userId ? subject.userId : 'Not Assigned';

                tableBody.append(`
                     <tr>
                        <td>${subject.id}</td>
                        <td>${subject.name}</td>
                        <td>${subject.st_count}</td>
                        <td>${userIdDisplay}</td> 
                        <td>${subject.date}</td>
                        <td>${subject.time}</td>
                     </tr>
                `);
            });
        },
        error: function () {
            alert("Error loading subjects.");
        }
    });
}

function loadUserIds() {
    $.ajax({
        url: "http://localhost:8080/api/v1/user/get",
        type: "GET",
        success: function (data) {
            let cmbCustomer = $("#u_id");
            cmbCustomer.empty();
            cmbCustomer.append(`<option value="">Select Admin</option>`);

            data.forEach(user => {
                if (user.role.toLowerCase() === "admin") {
                    cmbCustomer.append(`<option value="${user.u_id}">${user.u_id}</option>`);
                }
            });
        },
        error: function () {
            alert("Error loading users.");
        }
    });
}



function deleteData() {
    let id = $("#id").val();

    $.ajax({
        url: `http://localhost:8080/api/v1/subject/delete/${id}`,
        type: "DELETE",
        success: function () {
            alert("Subject deleted successfully!");
            loadAllSubjects();
            loadNextId();
        },
        error: function () {
            alert("Error deleting Subject.");
        }
    });
}
