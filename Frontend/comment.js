const COMMENT_API = "http://localhost:8080/posts";

async function addComment(postid) {
    const input = document.getElementById(`comment-${postid}`);
    const content = input.value.trim();

    if (!content) {
        return;
    }
    try {
        const res = await fetch(`${COMMENT_API}/${postid}/comment`, {
            method: "POST",
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token"),
                "Content-Type": "text/plain"
            },
            body: content
        });
        if (!res.ok) {
            throw new Error("comment failed");
        }
         const updatedPost = await res.json(); // updated post with comments
        const commentsContainer = document.getElementById(`comments-${postid}`);
        commentsContainer.innerHTML = renderComments(updatedPost.comments || []);
        input.value = "";
        
    }
    catch (err) {
        console.error(err);
        alert("unable to add comment");
    }
}