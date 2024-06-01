import { header, token } from "./util/csrf.js";

const accountId = document.getElementById("accountId")

const firstName = document.getElementById("firstName")
const lastName = document.getElementById("lastName")
const email = document.getElementById("email")

async function getAccount() {
    const response = await fetch(`/api/account/${accountId.value}`, {
        method: "GET",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json",
            [header]: token
        },
        body: JSON.stringify({
            first_name: firstName.value,
            last_name: lastName.value,
            email: email.value
        })
    });
    if (response.status === 200) {
        const account = await response.json();
    } else {
        alert("Something went wrong!");
    }
}

const updateButton = document.getElementById("updateButton")
const firstTextArea = document.getElementById("firstNameTextArea");
const lastTextArea = document.getElementById("lastNameTextArea");
const emailTextArea = document.getElementById("emailTextArea");


async function changeIssue() {
    const response = await fetch(`/api/account/${accountId.value}`, {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json",
            [header]: token
        },
        body: JSON.stringify({
            firstName: firstTextArea.value,
            lastName: lastTextArea.value,
            email: emailTextArea.value
        })
    })
    if (response.status === 204) {
        console.log("done")
    } else {
        alert('Something went wrong!'); // Don't use alerts in a "real" application.
    }
}

updateButton?.addEventListener("click", changeIssue);
