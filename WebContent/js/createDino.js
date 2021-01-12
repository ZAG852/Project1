let dinoBtn = document.getElementById("dinoBtn");
let dinoName = document.getElementById("dinoName");
let dinoPeriod = document.getElementById("dinoPeriod");
let url = 'http://13.59.187.62:8080/P0Server/dino';
const createDino = () =>{
    if(dinoName.value && dinoPeriod.value)
    {
        let xhr = new XMLHttpRequest();
		const newUrl = new URL(url);
        newUrl.searchParams.set('period',dinoPeriod.value);
        newUrl.searchParams.set('name',dinoName.value);
        xhr.open("POST", newUrl);
        xhr.onreadystatechange= ()=>{
            if(xhr.readyState !== 4)
            return;
            if(xhr.status === 200 || xhr.status ===201)
            {
            	window.location.href = '/P0Server/dino-list';
                console.log(xhr.responseText);
            }
            else{
                console.log("HTTP error", xhr.status, xhr.statusText);
            }
        };
        xhr.send();
        
        
        xhr.timeout=5000;
        xhr.ontimeout= ()=>console.log('timed out', xhr.responseURL);
    }
    else{
        console.log("A field is blank")
        alert("A field is blank!(Note for Developer: 'Update later')");
    }
}
dinoBtn.addEventListener("click", createDino)