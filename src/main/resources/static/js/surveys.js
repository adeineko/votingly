const listElement = document.getElementById('surveys-list');

async function getSurveys() {
    const response = await fetch('/api/forms',
        {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

    if (response.status === 200) {
        const data = await response.json();
        data.forEach(form => {
            const listItem = document.createElement('li');
            listItem.innerText = form.formName;
            console.log(form.formName)
            listItem.addEventListener('click', () => {
                window.location.href = `/surveys/${form.id}/questions`;
            });
            listElement.appendChild(listItem);
        });

    }
}

window.addEventListener('load', () => getSurveys());
