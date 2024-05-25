document.addEventListener("DOMContentLoaded", function () {
  const mobileMenu = document.querySelector(".mobile-menu");
  const menu = document.querySelector(".menu");

  mobileMenu.addEventListener("click", function () {
    menu.classList.toggle("show");
  });
});

function processRegistration(event) {
  event.preventDefault();
  let username = document.getElementById("username").value;
  let password = document.getElementById("password").value;

  if (username && password) {
    // Retrieve existing users from localStorage
    let registeredUsers = localStorage.getItem("RegisteredUsers");
    if (!registeredUsers) {
      registeredUsers = "";
    }

    // Append new registration
    registeredUsers += `${username}:${password};`;
    localStorage.setItem("RegisteredUsers", registeredUsers);

    alert("Registration successful");
  } else {
    alert("Please fill out both fields");
  }
}

function processLogin(event) {
  event.preventDefault();
  let usernameEntered = document.getElementById("username").value;
  let passwordEntered = document.getElementById("password").value;

  const registeredUsers = localStorage.getItem("RegisteredUsers");
  let loginStatus = false;
  let message = "";

  if (registeredUsers) {
    let usernamePasswordPairs = registeredUsers
      .split(";")
      .filter((pair) => pair.trim() !== "");

    for (let i = 0; i < usernamePasswordPairs.length; i++) {
      let [registeredUsername, registeredPassword] =
        usernamePasswordPairs[i].split(":");
      if (
        usernameEntered === registeredUsername &&
        passwordEntered === registeredPassword
      ) {
        loginStatus = true;
        break;
      }
    }

    message = loginStatus
      ? "Login successful"
      : "Login failed, invalid credentials";
  } else {
    message = "No one has registered!";
  }

  alert(message);
}
