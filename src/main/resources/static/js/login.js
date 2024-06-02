const pwShowHide = document.querySelectorAll(".eye-icon");
const signInButton = document.getElementById("signInButton");
const signUpButton = document.getElementById("signUpButton");

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
function redirectToIndex() {
    window.location.href = "/";
}

function redirectToSignUp() {
    window.location.href = "/signup"
}

signInButton.addEventListener("click", redirectToIndex);
signUpButton.addEventListener("click", redirectToSignUp);

