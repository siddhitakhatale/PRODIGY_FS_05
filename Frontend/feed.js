const API_URL = "http://localhost:8080/posts";
const feed = document.getElementById("feed");

document.addEventListener("DOMContentLoaded", () => {
    loadFeed();
});

async function loadFeed() {
    try {
        const res = await fetch(`${API_URL}/feed`, {
            method: "GET",
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token"),

            }
        });
        if (!res.ok) {
            throw new Error("failed to load the posts");
        }
        const posts = await res.json();
        feed.innerHTML = "";
        posts.forEach(post => {
            feed.innerHTML += createPostCard(post);
            connectWebSocket(post.postid);
        });

    }
    catch (err) {
        console.error(err);
        feed.innerHTML = "<p>Unable to load feed</p>"
    }
}
function createPostCard(post) {
    return `
    <div class="post">
      <div class="post-header">
        <i class="fa-solid fa-user"></i>
        <strong>${post.user?.username}</strong>
      </div>

      ${post.imageUrl ? `<img src="${post.imageUrl}" />` : ""}
      ${post.videoUrl ? `<video controls src="${post.videoUrl}"></video>` : ""}

      <div class="post-actions">
        <div class="one"><button onclick="likePost(${post.postid})"><i class="fa-solid fa-heart"></i></button>
        <span id="likes-${post.postid}">${post.likesCount || 0} likes</span></div>
        
        
         <button class="comment-btn" onclick="toggleComments(${post.postid})">
          <i class="fa-solid fa-comment"></i> Comments
      </button>
    </div>
    <div class="next">
       <p><strong>${post.user?.username || "User"}</strong> ${post.content}</p>
       <div class="comments hidden" id="comments-${post.postid}">
        ${renderComments(post.comments || [])}
      </div>
      
      <div class="comment-input">
           <input type="text" id="comment-${post.postid}" placeholder="Add a comment..."/>
           <button onclick="addComment(${post.postid})">Post</button>
       </div>
    </div>
     </div>
  `;
}
function toggleComments(postid){
    const box=document.getElementById(`comments-${postid}`);
    if(!box){
        return;
    }
    box.classList.toggle("hidden");
}
async function likePost(postid) {
    try {
        const res = await fetch(`${API_URL}/${postid}/like`, {
            method: "POST",
            headers: {
                "Authorization": "Bearer " + localStorage.getItem("token")
            }
        });
        if (!res.ok) {
            throw new Error();
        }
        const likes = await res.json();
        document.querySelector(`#likes-${postid}`).innerText = `${likes} likes`;


    }
    catch (error) {
        console.log("like failed");
    }
}

function renderComments(comments) {
    return comments.map(c => `
        <p class="comment">
           <strong>${c.user?.username || "User"}:</strong>
           ${escapeHTML(c.content)}
        </p>
    `).join("");
}
function escapeHTML(text = "") {
    return text.replace(/[&<>"']/g, m => ({
        '&': '&amp;',
        '<': '&lt;',
        '>': '&gt;',
        '"': '&quot;',
        "'": '&#039;'
    })[m]);
}
