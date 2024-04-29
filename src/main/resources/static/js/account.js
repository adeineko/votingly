import { header, token } from "./util/csrf.js";


const accountId = document.getElementById("accountId")

const firstName = document.getElementById("firstName")
const lastName = document.getElementById("lastName")
const email = document.getElementById("email")

const myAccounts = document.getElementById("myAccount");


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
        alert("Something went wrong!"); // alerts are "bad"...
    }
}

for (const myAccount of myAccounts) {
    myAccount.addEventListener("click", getAccount());
}
