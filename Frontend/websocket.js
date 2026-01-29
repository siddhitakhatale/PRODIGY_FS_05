let stompClient=null;
const subscribedPosts=new Set();

function connectWebSocket(postid){
    if(stompClient && stompClient.connected){
        subscribeToPost(postid);
        return;
    }
    const socket=new SockJS("http://localhost:8080/ws");
    stompClient=Stomp.over(socket);
    stompClient.debug=null;

    stompClient.connect({},() => {
        console.log("websockey connected");
        subscribeToPost(postid);
        subscribeToNotifications();
    });
}
function subscribeToPost(postid){
    if(!stompClient || !stompClient.connected){
        return;
    }
    if(subscribedPosts.has(postid)){
        return;
    }
    stompClient.subscribe(`/topic/comments/${postid}`,(msg) => {
        const comment=JSON.parse(msg.body);
        appendComment(postid,comment);
    })
    subscribedPosts.add(postid);
}
function appendComment(postid,comment){
    const container=document.getElementById(`comments-${postid}`);
    if(!container){
        return;
    }
    const html=`
        <p class="comment">
           <strong> ${comment.user?.username || "User"}:</strong>
           ${escapeHTML(comment.content)}
        </p>
    `
    container.insertAdjacentHTML("beforeend",html);
}
function subscribeToNotifications(){
    if(!stompClient || !stompClient.connected){
        return;
    }
    stompClient.subscribe("/user/queue/notifications",(msg) => {
        const notification=JSON.parse(msg.body);
        showLiveNotification(notification);
    })

}