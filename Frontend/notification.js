const NOTIF_API = "http://localhost:8080/notifications";

document.addEventListener("DOMContentLoaded", () => {
    if (!token) {
        alert("please login first");
        window.location.href = "login.html";
    }
    loadUnreadCount();
})

window.toggleNotifications= function() {
    const box = document.getElementById("notification-box");
    if (!box) {
        return;
    }
    box.classList.toggle("hidden")
    if (!box.classList.contains("hidden")) {
        loadNotifications();
    }
}
async function loadUnreadCount() {
    try {
        const res = await fetch(`${NOTIF_API}/unread-count`, {
            headers: {
                "Authorization": "Bearer " + token
            }
        })
        if (!res.ok) {
            return;
        }
        const count = await res.text();
        document.getElementById("notif-count").innerText = count;
    }
    catch (e) {
        console.error("Unread count errorc", e);
    }
}
async function loadNotifications() {
    try {
        const res = await fetch(`${NOTIF_API}`, {
            headers: {
                "Authorization": "Bearer " +token
            }
        });
        if (!res.ok) {
            return;
        }
        const notifications = await res.json();
        const box = document.getElementById("notification-box");
        if (!box) {
            return;
        }
        box.innerHTML = "";
        notifications.forEach(n => {
            box.innerHTML += `
                <div class="notification ${n.read ? "" : "unread"}"
                    onclick="markAsRead(${n.id})">
                    ${n.type}
                    ${n.message}
                </div>
            `;
        });
    }
    catch (e) {
        console.error("notification load error", e);
    }
}

async function markAsRead(id) {
    try {
        await fetch(`${NOTIF_API}/${id}/read`, {
            method: "PUT",
            headers: {
                "Authorization": "Bearer " +token
            }
        })
        loadNotifications();
        loadUnreadCount();
    }
    catch (e) {
        console.error("mark read failed", e);
    }
}

function showLiveNotification(notification){
    const box=document.getElementById("notification-box");
    const countEl=document.getElementById("notif-count");

    if(!box || !countEl){
        return;
    }
    countEl.innerText=parseInt(countEl.innerText || "0")+1;
    const div=document.createElement("div");
    div.className="notification unread";
    div.innerText=notification.content || notification.message;
    box.prepend(div);
}