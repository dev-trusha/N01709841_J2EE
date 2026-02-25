<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Admin Panel</title>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container py-4" style="max-width: 980px;">
  <div class="d-flex justify-content-between align-items-center mb-3">
    <h2 class="mb-0">Admin Panel</h2>
    <div class="d-flex gap-2">
      <a class="btn btn-outline-secondary" href="resources.jsp">Resources</a>
      <a class="btn btn-outline-danger" href="logout.jsp">Logout</a>
    </div>
  </div>

  <div id="msg"></div>

  <div class="card shadow-sm rounded-4 mb-3">
    <div class="card-body">
      <h5 class="mb-3">Create Resource</h5>
      <div class="row g-2">
        <div class="col-md-4">
          <input id="name" class="form-control" placeholder="Name (e.g., Study Room A)">
        </div>
        <div class="col-md-3">
          <select id="type" class="form-select">
            <option>ROOM</option>
            <option>LAB</option>
            <option>EQUIPMENT</option>
          </select>
        </div>
        <div class="col-md-3">
          <input id="location" class="form-control" placeholder="Location">
        </div>
        <div class="col-md-2 d-grid">
          <button class="btn btn-primary" onclick="createRes()">Add</button>
        </div>
      </div>
    </div>
  </div>

  <div class="card shadow-sm rounded-4">
    <div class="table-responsive">
      <table class="table table-hover mb-0">
        <thead>
        <tr>
          <th>ID</th><th>Name</th><th>Type</th><th>Location</th><th>Status</th><th></th>
        </tr>
        </thead>
        <tbody id="rows"></tbody>
      </table>
    </div>
  </div>
</div>

<script>
  const base = "<%=request.getContextPath()%>";
  const token = localStorage.getItem("token");
  const role = localStorage.getItem("role");
  if(!token) window.location.href="login.jsp";
  if(role !== "admin") window.location.href="resources.jsp";

  function show(type, text){
    document.getElementById("msg").innerHTML =
            "<div class='alert alert-" + type + " rounded-4'>" + text + "</div>";
  }

  function esc(s){
    if (s === null || s === undefined) return "";
    return String(s).replaceAll("&","&amp;").replaceAll("<","&lt;").replaceAll(">","&gt;");
  }

  async function load(){
    const rows = document.getElementById("rows");
    rows.innerHTML = "<tr><td colspan='6' class='text-muted'>Loading...</td></tr>";

    const res = await fetch(base + "/api/resources", { headers: { "Authorization":"Bearer " + token }});
    if(!res.ok){ rows.innerHTML = "<tr><td colspan='6' class='text-danger'>API Error " + res.status + "</td></tr>"; return; }

    const data = await res.json();
    if(data.length===0){ rows.innerHTML="<tr><td colspan='6' class='text-muted'>No resources</td></tr>"; return; }

    rows.innerHTML="";
    data.forEach(r=>{
      const tr = document.createElement("tr");
      tr.innerHTML =
              "<td>" + esc(r.id) + "</td>" +
              "<td>" + esc(r.name) + "</td>" +
              "<td>" + esc(r.type) + "</td>" +
              "<td>" + esc(r.location || "") + "</td>" +
              "<td>" + esc(r.status || "") + "</td>" +
              "<td><button class='btn btn-sm btn-outline-danger' onclick='del(" + r.id + ")'>Delete</button></td>";
      rows.appendChild(tr);
    });
  }

  async function createRes(){
    const payload = {
      name: document.getElementById("name").value.trim(),
      type: document.getElementById("type").value,
      location: document.getElementById("location").value.trim(),
      status: "ACTIVE"
    };

    const res = await fetch(base + "/api/resources", {
      method:"POST",
      headers: {"Content-Type":"application/json", "Authorization":"Bearer " + token},
      body: JSON.stringify(payload)
    });

    const text = await res.text();
    if(res.status===201){ show("success","Created"); load(); }
    else show("danger","Error ("+res.status+"): "+text);
  }

  async function del(id){
    const res = await fetch(base + "/api/resources/" + encodeURIComponent(id), {
      method:"DELETE",
      headers: {"Authorization":"Bearer " + token}
    });
    if(res.status===204){ show("success","Deleted"); load(); }
    else show("danger","Delete failed ("+res.status+")");
  }
  console.log("username:", localStorage.getItem("username"));
  console.log("role:", localStorage.getItem("role"));
  console.log("token exists:", !!localStorage.getItem("token"));
  load();
</script>
</body>
</html>