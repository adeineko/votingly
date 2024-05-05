document.getElementById('subscribeBtn').addEventListener('click', function () {
    document.getElementById('popup').style.display = 'block';
});

document.querySelector('.close').addEventListener('click', function () {
    document.getElementById('popup').style.display = 'none';
});

document.getElementById('subscribeSubmit').addEventListener('click', function () {
    var email = document.getElementById('emailInput').value;
    console.log('Subscribed with email: ' + email);
    document.getElementById('popup').style.display = 'none';
});