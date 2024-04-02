// const accountId = document.getElementById("accountId")
//
// const firstName = document.getElementById("firstName")
// const lastName = document.getElementById("lastName")
// const email = document.getElementById("email")
//
//
// async function getAccount() {
//     const response = await fetch(`/api/account/${accountId.value}`, {
//         method: "GET",
//         headers: {
//             "Accept": "application/json",
//             "Content-Type": "application/json",
//         },
//         body: JSON.stringify({
//             first_name: firstName.value,
//             last_name: lastName.value,
//             email: email.value
//         })
//     });
//     if (response.status === 200) {
//         const account = await response.json();
//     } else {
//         alert("Something went wrong!"); // alerts are "bad"...
//     }
// }
