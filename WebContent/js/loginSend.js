let showBtn = document.getElementById('changeVisibility');

let isClicked = false;
let loginBtn = document.getElementById('loginBtn');
let url = 'http://13.59.187.62:8080/P0Server/login';
const show = (event) => {
    
    if(!isClicked)
    {
        document.getElementById('passField').type ='text';
        isClicked= true;
    }
    else{
        document.getElementById('passField').type= 'password';
        isClicked =false;
    }
    console.log(isClicked)
} 
const login = () =>{
    let password = document.getElementById('passField');
    let username = document.getElementById('userField');
    console.log("to be sent " + userField.value);
    console.log("to be sent " + passField.value);
    if(userField.value && passField.value)
    {
        let xhr = new XMLHttpRequest();
		const newUrl = new URL(url);
        newUrl.searchParams.set('username',username.value);
        newUrl.searchParams.set('password',password.value);
        xhr.open("POST", newUrl);
        xhr.onreadystatechange= ()=>{
            if(xhr.readyState !== 4)
            return;
            if(xhr.status === 200 || xhr.status ===201)
            {
            	window.location.href = '/P0Server';
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
showBtn.addEventListener('click',show);
loginBtn.addEventListener('click', login);