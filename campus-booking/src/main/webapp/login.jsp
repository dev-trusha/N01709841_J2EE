<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container py-5" style="max-width:520px;">
    <div class="card shadow-sm rounded-4">
        <div class="card-body p-4">
            <h3 class="mb-3">Sign in</h3>
            <div id="msg"></div>

            <div class="mb-3">
                <label class="form-label">Username</label>
                <input id="u" class="form-control" placeholder="admin / student">
            </div>
            <div class="mb-3">
                <label class="form-label">Password</label>
                <input id="p" type="password" class="form-control" placeholder="adminpass / studentpass">
            </div>

            <button class="btn btn-primary w-100" onclick="login()">Login</button>

            <div class="small text-muted mt-3">
                Demo users: <code>admin/adminpass</code> (admin), <code>student/studentpass</code> (user)
            </div>
        </div>
    </div>
</div>

<script>
    const base = "<%=request.getContextPath()%>";

    function show(type, text){
        document.getElementById("msg").innerHTML =
            "<div class='alert alert-" + type + " rounded-4'>" + text + "</div>";
    }

    async function login(){
        const payload = { username: document.getElementById("u").value.trim(),
            password: document.getElementById("p").value.trim() };

        const res = await fetch(base + "/api/auth/login", {
            method: "POST",
            headers: {"Content-Type":"application/json"},
            body: JSON.stringify(payload)
        });

        const txt = await res.text();
        if(res.status !== 200){
            show("danger", "Login failed");
            return;
        }

        const data = JSON.parse(txt);
        localStorage.setItem("token", data.token);
        localStorage.setItem("role", data.role);
        localStorage.setItem("username", payload.username);

        window.location.href = "resources.jsp";
    }
</script>
</body>
</html>