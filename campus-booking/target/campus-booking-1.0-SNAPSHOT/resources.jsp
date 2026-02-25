<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Resources</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container py-4">
  <div class="d-flex justify-content-between align-items-center mb-3">
    <h2 class="mb-0">Resources</h2>
    <div class="d-flex gap-2 flex-wrap">
      <a class="btn btn-outline-secondary" href="reservations.jsp">Reservations</a>
      <a id="adminBtn" class="btn btn-primary d-none" href="admin.jsp">Admin Panel</a>
      <a class="btn btn-outline-danger" href="logout.jsp">Logout</a>
    </div>
  </div>

  <div id="grid" class="row g-3"></div>
</div>

<script>
  const base = "<%=request.getContextPath()%>";
  const token = localStorage.getItem("token");
  const role = localStorage.getItem("role");

  if(!token){ window.location.href = "login.jsp"; }
  if(role === "admin"){ document.getElementById("adminBtn").classList.remove("d-none"); }

  function esc(s){
    if (s === null || s === undefined) return "";
    return String(s).replaceAll("&","&amp;").replaceAll("<","&lt;").replaceAll(">","&gt;");
  }

  async function loadResources(){
    const grid = document.getElementById("grid");
    grid.innerHTML = "<div class='text-muted'>Loading...</div>";

    const res = await fetch(base + "/api/resources", {
      headers: { "Authorization": "Bearer " + token }
    });

    if(!res.ok){
      grid.innerHTML = "<div class='alert alert-danger rounded-4'>API Error: " + res.status + "</div>";
      return;
    }

    const data = await res.json();
    if(data.length === 0){
      grid.innerHTML = "<div class='alert alert-warning rounded-4'>No resources yet. (Admin can add in Admin Panel)</div>";
      return;
    }

    grid.innerHTML = "";
    data.forEach(function(r){
      const status = r.status || "ACTIVE";
      const badgeClass = (status === "ACTIVE") ? "bg-success" : "bg-warning text-dark";
      const location = r.location ? r.location : "";

      const col = document.createElement("div");
      col.className = "col-12 col-md-6 col-lg-4";
      col.innerHTML =
              "<div class='card shadow-sm rounded-4 h-100'>" +
              "<div class='card-body'>" +
              "<div class='d-flex justify-content-between align-items-start'>" +
              "<h5 class='card-title mb-1'>" + esc(r.name) + "</h5>" +
              "<span class='badge " + badgeClass + "'>" + esc(status) + "</span>" +
              "</div>" +
              "<div class='text-muted small mb-3'>" + esc(r.type) + " • " + esc(location) + "</div>" +
              "<a class='btn btn-primary btn-sm' href='book.jsp?resourceId=" + encodeURIComponent(r.id) + "'>Book</a>" +
              "</div>" +
              "</div>";
      grid.appendChild(col);
    });
  }

  loadResources();
</script>
</body>
</html>