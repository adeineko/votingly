const pwShowHide = document.querySelectorAll(".eye-icon");
// const signInButton = document.getElementById("signInButton");

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
// signInButton?.addEventListener("click", () => {window.location.href = `/`});

function redirectToIndex() {
    window.location.href = "/"; // Change "/index" to the actual URL of your index page
}
