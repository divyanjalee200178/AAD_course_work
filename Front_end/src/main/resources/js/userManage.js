$(document).ready(function () {
    loadNextId();
    $('#dataTable').on('click', 'tr', function () {
        var userId = $(this).find('td:eq(0)').text();
        var name = $(this).find('td:eq(1)').text();
        var contact = $(this).find('td:eq(2)').text();
        var address = $(this).find('td:eq(3)').text();
        var email = $(this).find('td:eq(4)').text();
        var role = $(this).find('td:eq(5)').text();

        $('#u_id').val(userId);
        $('#name').val(name);
        $('#contact').val(contact);
        $('#address').val(address);
        $('#email').val(email);
        $('#role').val(role);
    });
});


function saveData() {
    let user = {
        name: $("#name").val(),
        contact: $("#contact").val(),
        address: $("#address").val(),
        email: $("#email").val(),
        role: $("#role").val(),
        password: $("#password").val()
    };

    $.ajax({
        url: "http://localhost:8080/api/v1/user/save",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(user),
        success: function () {
            alert("Customer saved successfully!");
            $("#userForm")[0].reset();
            loadNextId();
        },
        error: function (xhr) {
            alert("Error: " + xhr.responseText);
        }
    });
}

function loadNextId() {
    $.ajax({
        url: "http://localhost:8080/api/v1/user/next-id",
        type: "GET",
        success: function (nextId) {
            $('#u_id').val(nextId);
        },
        error: function () {
            alert("Error fetching next ID.");
        }
    });
}

function updateData() {
    let u_id = $("#u_id").val();
    let user = {
        u_id: u_id,
        name: $("#name").val(),
        contact: $("#contact").val(),
        address: $("#address").val(),
        email: $("#email").val(),
        role: $("#role").val(),
        password: $("#password").val()
    };

    console.log("Updating user:", user); // Log user data

    $.ajax({
        url: `http://localhost:8080/api/v1/user/update/${u_id}`,
        type: "PUT",
        contentType: "application/json",
        data: JSON.stringify(user),
        success: function () {
            alert("User updated successfully!");
            loadAllUsers();
        },
        error: function () {
            alert("Error updating User.");
        }
    });
}


function loadAllUsers() {
    $.ajax({
        url: "http://localhost:8080/api/v1/user/get",
        type: "GET",
        success: function (data) {
            let tableBody = $("#dataTable");
            tableBody.empty();
            data.forEach(user => {
                tableBody.append(`
                    <tr>
                        <td>${user.u_id}</td>
                        <td>${user.name}</td>
                        <td>${user.contact}</td>
                        <td>${user.address}</td>
                        <td>${user.email}</td>
                        <td>${user.role}</td>
                    </tr>
                `);
            });
        },
        error: function () {
            alert("Error loading customers.");
        }
    });
}

function deleteData() {
    let u_id = $("#u_id").val();

    $.ajax({
        url: `http://localhost:8080/api/v1/user/delete/${u_id}`,
        type: "DELETE",
        success: function () {
            alert("User deleted successfully!");
            loadAllUsers();
        },
        error: function () {
            alert("Error deleting User.");
        }
    });
}
