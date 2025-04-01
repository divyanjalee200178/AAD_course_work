$(document).ready(function () {

    $(".btn-primary").on("click", function (event) {
        event.preventDefault();


        var email = $("#inputEmail").val().trim();
        var password = $("#inputPassword").val().trim();


        if (!email || !password) {
            alert("Please fill in all required fields.");
            return;
        }


        $.ajax({
            url: 'http://localhost:8080/api/v1/auth/authenticate',
            method: 'POST',
            contentType: 'application/json',
            dataType: 'json',
            data: JSON.stringify({
                email: email,
                password: password
            }),

            success: function (response) {
                console.log("Login successful:", response);


                if (response && response.data && response.data.token) {
                    console.log("Token received:", response.data.token);
                    onLoginSuccess(response.data);


                    const token = response.data.token;
                    const decoded = jwt_decode(token);
                    let role = decoded.role || "";


                    console.log("Decoded token:", decoded);
                    console.log("Role from decoded token:", role);
                    // Redirect based on the role
                    if (role === "ROLE_ADMIN" || role === "Admin") {
                        window.location.href = "../static/dash.html";
                    } else if (role === "ROLE_STUDENT" || role === "Student") {
                        window.location.href = "../static/studentDash.html";
                    } else if (role === "ROLE_TEACHER" || role === "Teacher") {
                        window.location.href = "../static/studentDash.html";
                    } else {
                        alert("Invalid role: " + role);
                    }
                } else {
                    alert("Login successful, but token is missing. Please try again.");
                }
            },

            error: function (xhr, status, error) {
                alert("Login failed. Please check your credentials.");
            }
        });
    });

    // Function to save the token in localStorage
    function onLoginSuccess(response) {
        // Store the JWT token in localStorage
        localStorage.setItem("jwtToken", response.token);
        const token = localStorage.getItem("jwtToken");
        console.log("Token after saving:", token);
    }
});
