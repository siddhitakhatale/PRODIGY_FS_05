const token=localStorage.getItem("token");
if(!token){
    alert("please login first");
    window.location.href="login.html";
}
async function createPost() {
    const content = document.getElementById("postContent").value.trim();
    const media = document.getElementById("media").files[0];

    let imageUrl = null;
    let videoUrl = null;

    if (!content && !media) {
        alert("Please write something or upload media");
        return;
    }

    try {
      
        if (media) {
            const formData = new FormData();
            formData.append("file", media);
            formData.append("upload_preset", "socialapp_preset");

            let cloudinaryurl="";
            if(media.type.startsWith("image")){
                cloudinaryurl="https://api.cloudinary.com/v1_1/dvxlbsbeo/image/upload";
            }
            else if(media.type.startsWith("video")){
                cloudinaryurl="https://api.cloudinary.com/v1_1/dvxlbsbeo/video/upload";
            }

            const cloudinaryRes = await fetch(cloudinaryurl, {
                method: "POST",
                body: formData
            });
            if(!cloudinaryRes.ok){
                throw new Error("Cloudinary upload failed");
            }

            const cloudinaryData = await cloudinaryRes.json();

            if (media.type.startsWith("image")) {
                imageUrl = cloudinaryData.secure_url;
            } else if (media.type.startsWith("video")) {
                videoUrl = cloudinaryData.secure_url;
            }
        }

       
        const params = new URLSearchParams();
        params.append("content", content);

        if (imageUrl) params.append("imageUrl", imageUrl);
        if (videoUrl) params.append("videoUrl", videoUrl); // matches backend

        const res = await fetch("http://localhost:8080/posts", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
                "Authorization":"Bearer "+localStorage.getItem("token")
            },
            body: params
        });

        if (!res.ok) {
            throw new Error("Post creation failed");
        }

        document.getElementById("postContent").value = "";
        document.getElementById("media").value = "";

        loadFeed();

    } catch (err) {
        console.error(err);
        alert("Something went wrong while creating post");
    }
}
