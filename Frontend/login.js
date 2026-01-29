document.getElementById("loginForm").addEventListener("submit", async(e) =>{
    e.preventDefault();
    const email=e.target.email.value;
    const password=e.target.password.value;

    try{
        const res=await fetch("http://localhost:8080/auth/login",{
            method:"POST",
            headers:{
                "Content-Type":"application/json"
            },
            body:JSON.stringify({email,password})
        });
        if(!res.ok){
            throw new Error("unauthorized");
        }
        const data=await res.json();
        localStorage.setItem("token",data.token);
        localStorage.setItem("username",data.username);
        alert("login successful");
        window.location.href="home.html";
    }
    catch(err){
        alert("invalid credentials");
    }
})