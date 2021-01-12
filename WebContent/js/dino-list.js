let url = 'http://13.59.187.62:8080/P0Server/dino';
const btn = document.getElementById("searchIndex");
const searchBar = document.getElementById("search");
const placeDino= function(dinoId, dinoName, dinoPeriod) {
    const tableBody = document.getElementById('Dinosaur-table-data');

    const entry = document.createElement('tr');

    entry.innerHTML = 
    `<td>${dinoId}</td>
    <td>${dinoName}</td>
    <td>${dinoPeriod}</td>
    `;

    tableBody.appendChild(entry);
}
const delDino = ()=>{
    const ur = "http://13.59.187.62:8080/P0Server/dino/";
    const url2 = new URL(ur)

    const xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            
            console.log(xhr.responseText);
            window.location.href = '/P0Server/dino-list';
        }
    }

    xhr.open('DELETE', url2+searchBar.value);

    xhr.send();
}
const getAllDinos = function() {
    const xhr = new XMLHttpRequest();

    // There are essentially 5 different ready states
    // 0: Unsent
    // 1: Opened
    // 2: Headers_received
    // 3: Loading
    // 4: Done
    xhr.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            const data = JSON.parse(xhr.responseText);
            console.log(data);
            for (let i = 0; i < data.length; i++) {
                placeDino(data[i].dinoID, data[i].name, data[i].period);
            }
            console.log("Dino data received");
        }
    }

    xhr.open('GET', url);

    xhr.send();
}

getAllDinos();
btn.addEventListener('click',delDino);