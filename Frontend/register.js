document.addEventListener("DOMContentLoaded",() =>{
    document.getElementById("registerForm").addEventListener("submit",async(e) => {
    e.preventDefault();
    const form=e.target;
    const userData={
        username:form.username.value,
        email:form.email.value,
        password:form.password.value,
        bio:form.bio.value
    };
    try{
        const res=await fetch("http://localhost:8080/auth/register",{
            method:"POST",
            headers:{"Content-Type":"application/json"},
            body:JSON.stringify(userData)
        });
        if(!res.ok){
            throw new Error("registration failed");
        }
        alert("registration done successfully");
        window.location.href="login.html";
    }
    catch(err){
        alert("error "+ err.message);
    }
});
});