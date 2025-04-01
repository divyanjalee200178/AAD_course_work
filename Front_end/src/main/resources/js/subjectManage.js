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
    let token = localStorage.getItem("token");

    if (!token) {
        alert("Not authenticated!");
        return;
    }

    fetch("http://localhost:8080/api/v1/subject/next-id", {
        method: "GET",
        headers: {
            "Authorization": "Bearer " + token,
            "Content-Type": "application/json"
        }
    })
        .then(response => {
            if (!response.ok) throw new Error("Unauthorized access!");
            return response.text();
        })
        .then(nextId => {
            document.getElementById("id").value = nextId;
        })
        .catch(error => {
            console.error("Error fetching next ID:", error);
            alert("Authentication required!");
        });
}

function saveData() {
    let token = localStorage.getItem("token");

    if (!token) {
        alert("You need to log in!");
        return;
    }

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
        headers: { "Authorization": "Bearer " + token },  // Add JWT Token
        data: JSON.stringify(subData),
        success: function (resp) {
            alert(resp.msg);
            console.log("Success:", resp);
            loadAllSubjects();
            loadNextId();
            $("#userForm")[0].reset();
        },
        error: function (xhr) {
            if (xhr.status === 403) {
                alert("Access denied! You may not have permission.");
            } else {
                try {
                    let response = JSON.parse(xhr.responseText);
                    alert("Error: " + response.message);
                    console.log("Error:", response);
                } catch (e) {
                    alert("Unexpected error occurred!");
                    console.log("Parsing error:", e);
                }
            }
        }
    });
}



// Function to update the subject data
function updateData() {

    const id = $('#id').val();  // Get ID from the input field
    const name = $('#name').val();  // Get name from the input field
    const st_count = $('#st_count').val();  // Get student count from the input field
    const u_id = $('#u_id').val();  // Get user ID from the select field
    const date = $('#date').val();  // Get date from the input field
    const time = $('#time').val();  // Get time from the input field

    const updatedSubject = {
        id: id,
        name: name,
        st_count: st_count,
        userId: u_id,
        date: date,
        time: time
    };

    const token = localStorage.getItem('token'); // Get JWT token from localStorage
    if (!token) {
        console.error('No token found in localStorage');
        return;
    }


    // Sending the updated data to the backend via PUT request
    $.ajax({
        url: `http://localhost:8080/api/v1/subject/update`,  // Send PUT request with the subject ID
        type: 'PUT',
        headers: {
            'Authorization': `Bearer ${token}`  // Include JWT token for authorization
        },
        data: JSON.stringify(updatedSubject),  // Send updated data in JSON format
        contentType: 'application/json',
        success: function(response) {
            console.log('Subject updated successfully:', response);
            loadAllSubjects();  // Reload subjects to reflect updated data
        },
        error: function(xhr, status, error) {
            console.error('Error updating subject:', xhr.responseText);
            alert('Failed to update subject. Please check the console for errors.');
        }
    });
}

function loadAllSubjects() {
    let token = localStorage.getItem("token");
    if (!token) {
        alert("You need to log in!");
        return;
    }

    $.ajax({
        url: "http://localhost:8080/api/v1/subject/get",
        type: "GET",
        headers: { "Authorization": "Bearer " + token },
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
        error: function (xhr) {
            console.error("Error loading subjects:", xhr);
            alert("Error loading subjects. Check console for details.");
        }
    });
}

function loadUserIds() {
    let token = localStorage.getItem("token");
    if (!token) {
        alert("You need to log in!");
        return;
    }

    $.ajax({
        url: "http://localhost:8080/api/v1/user/get",
        type: "GET",
        headers: { "Authorization": "Bearer " + token },  // Add JWT Token
        success: function (data) {
            let cmbCustomer = $("#u_id");
            cmbCustomer.empty();
            cmbCustomer.append(`<option value="">Select ADMIN</option>`);

            data.forEach(user => {
                // Only show users with role 'admin'
                if (user.role && user.role.toLowerCase() === "admin") {
                    cmbCustomer.append(`<option value="${user.u_id}">${user.u_id}</option>`);
                }
            });
        },
        error: function (xhr) {
            console.error("Error loading users:", xhr);
            alert("Error loading users. Check console for details.");
        }
    });
}


function deleteData() {
    let id = $("#id").val();  // Get the id of the subject to delete

    if (!id) {
        alert("Please select a subject to delete.");
        return;
    }

    let token = localStorage.getItem("token");  // Get JWT token from localStorage

    if (!token) {
        alert("You need to log in!");
        return;
    }

    // Send the DELETE request to the backend
    $.ajax({
        url: `http://localhost:8080/api/v1/subject/delete/${id}`,  // Correct URL to match your backend route
        type: "DELETE",
        headers: {
            "Authorization": "Bearer " + token  // Include token in headers for authentication
        },
        success: function () {
            alert("Subject deleted successfully!");  // Alert on success
            loadAllSubjects();  // Reload the list of subjects
            loadNextId();  // Optionally reload the next available ID if needed
        },
        error: function (xhr, status, error) {
            console.error("Error deleting subject:", error);
            if (xhr.status === 404) {
                alert("Subject not found.");
            } else {
                alert("Error deleting subject. Please try again.");
            }
        }
    });
}

