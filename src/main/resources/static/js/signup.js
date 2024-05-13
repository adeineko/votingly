import { header, token } from "./util/csrf.js";

const pwShowHide = document.querySelectorAll(".eye-icon");

pwShowHide.forEach(eyeIcon => {
    eyeIcon.addEventListener("click", () => {
        let pwFields = eyeIcon.parentElement.parentElement.querySelectorAll(".password");

        pwFields.forEach(password => {
            if(password.type === "password"){
                password.type = "text";
                eyeIcon.classList.replace("bx-hide", "bx-show");
                return;
            }
            password.type = "password";
            eyeIcon.classList.replace("bx-show", "bx-hide");
        })

    })
})


document.addEventListener('DOMContentLoaded', function () {
    const signupForm = document.getElementById("signup");

    signupForm.addEventListener('submit', function (event) {
        event.preventDefault(); // Prevent the default form submission

        // Collect user input data
        const firstName = document.getElementById("firstName").value;
        const lastName = document.getElementById("lastName").value;
        const email = document.getElementById("email").value;
        const password = document.getElementById("password").value;
        // const user_type = document.

        // // Create a user object
        // const user = {
        //     firstName: firstName,
        //     lastName: lastName,
        //     email: email,
        //     password: password
        // };

        // Send a POST request to the backend API
        fetch('/api/signup', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                [header]: token
            },
            body: JSON.stringify({
                firstName: firstName,
                lastName: lastName,
                email: email,
                password: password
            })
        })
            .then(response => {
                if (response.ok) {
                    // If the request was successful, redirect to a success page or do something else
                    window.location.href = '/login'; // Redirect to success page
                } else {
                    // If the request was not successful, handle the error
                    return response.json().then(data => {
                        throw new Error(data.message || 'Error occurred during signup. Please try again later.');
                    });
                }
            })
            .catch(error => {
                console.error('Error:', error.message);
                // Display the error message to the user or handle it appropriately
                alert(error.message);
            });
    });
});

