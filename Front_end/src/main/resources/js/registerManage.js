function saveData() {
    let roleMap = {
        "ADMIN": "Admin",
        "TEACHER": "Teacher",
        "STUDENT": "Student"
    };

    let registerData = {
        u_id: $("#u_id").val(),
        name: $("#name").val(),
        contact: $("#contact").val(),
        address: $("#address").val(),
        email: $("#email").val(),
        role: roleMap[$("#role").val()],
        password: $("#password").val()
    };

    $.ajax({
        url: "http://localhost:8080/api/v1/user/register",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(registerData),
        success: function (response) {
            alert("Registration successful!");
            window.location.href = "login.html";
        },
        error: function (xhr) {
            alert("Registration failed: " + (xhr.responseJSON ? xhr.responseJSON.message : "Unknown error"));
        }
    });
}
