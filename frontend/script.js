// script.js
document.addEventListener('DOMContentLoaded', () => {
    // DOM Elements
    const authContainer = document.getElementById('auth-container');
    const chatContainer = document.getElementById('chat-container');
    const loginForm = document.getElementById('login-form');
    const registerForm = document.getElementById('register-form');
    const showRegisterLink = document.getElementById('show-register');
    const showLoginLink = document.getElementById('show-login');
    const registerBox = document.getElementById('register-box');
    const loginBox = document.getElementById('login-box');
    const chatLog = document.getElementById('chat-log');
    const messageInput = document.getElementById('message-input');
    const sendButton = document.getElementById('send-button');
    const userList = document.getElementById('user-list');

    // Variables for chat state
    let stompClient = null;
    let username = null;
    let token = null;
    let currentChatRecipient = null;

    // Toggle between login and register forms
    showRegisterLink.addEventListener('click', (e) => {
        e.preventDefault();
        loginBox.style.display = 'none';
        registerBox.style.display = 'block';
    });

    showLoginLink.addEventListener('click', (e) => {
        e.preventDefault();
        registerBox.style.display = 'none';
        loginBox.style.display = 'block';
    });

    // Handle Registration Form Submission
    registerForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        username = e.target.elements['register-username'].value;
        const password = e.target.elements['register-password'].value;

        try {
            const response = await fetch('http://localhost:8080/api/auth/register', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username, password })
            });

            if (response.ok) {
                alert('Registration successful! Please log in.');
                loginBox.style.display = 'block';
                registerBox.style.display = 'none';
            } else {
                const error = await response.text();
                alert('Registration failed: ' + error);
            }
        } catch (error) {
            console.error('Error during registration:', error);
            alert('An error occurred. Please try again.');
        }
    });

    // Handle Login Form Submission
    loginForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        username = e.target.elements['login-username'].value;
        const password = e.target.elements['login-password'].value;

        try {
            const response = await fetch('http://localhost:8080/api/auth/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username, password })
            });

            if (response.ok) {
                token = await response.text();
                localStorage.setItem('jwtToken', token);
                localStorage.setItem('username', username);
                
                authContainer.style.display = 'none';
                chatContainer.style.display = 'block';
                document.getElementById('current-user').innerText = `Welcome, ${username}`;
                
                connectWebSocket();
            } else {
                const error = await response.text();
                alert('Login failed: ' + error);
            }
        } catch (error) {
            console.error('Error during login:', error);
            alert('An error occurred. Please try again.');
        }
    });

    // WebSocket Connection Function
    function connectWebSocket() {
        const socket = new SockJS('http://localhost:8080/ws');
        stompClient = Stomp.over(socket);

        const headers = { 'X-Authorization': 'Bearer ' + token };

        stompClient.connect(headers, function (frame) {
            console.log('Connected: ' + frame);

            stompClient.subscribe('/topic/public', function (message) {
                showMessage(JSON.parse(message.body));
            });

            stompClient.subscribe('/user/' + username + '/private', function (message) {
                showMessage(JSON.parse(message.body));
            });
            
            fetchUsers();
        }, function(error) {
            console.error("WebSocket connection error: ", error);
            alert("Could not connect to the chat server. Please try again.");
        });
    }

    // Fetch and display users list
    async function fetchUsers() {
        try {
            const response = await fetch('http://localhost:8080/api/users', {
                headers: { 'Authorization': 'Bearer ' + token }
            });

            if (!response.ok) {
                throw new Error('Failed to fetch users');
            }

            const users = await response.json();
            userList.innerHTML = '';
            users.forEach(user => {
                const userElement = document.createElement('li');
                userElement.innerText = user.username;
                userElement.classList.add('user-item');
                userElement.addEventListener('click', () => {
                    startPrivateChat(user.username);
                });
                userList.appendChild(userElement);
            });
        } catch (error) {
            console.error('Error fetching users:', error);
        }
    }
    
    // Private chat shuru karen
    function startPrivateChat(recipient) {
        currentChatRecipient = recipient;
        document.getElementById('current-user').innerText = `Chat with ${recipient}`;
        chatLog.innerHTML = ''; // Naya chat shuru hone par purani messages clear karen
    }

    // Send message logic
    const sendMessage = () => {
        const messageContent = messageInput.value.trim();
        if (messageContent && stompClient) {
            const chatMessage = {
                senderId: username,
                content: messageContent,
                receiverId: currentChatRecipient // Private chat ke liye
            };

            if (currentChatRecipient) {
                // Private message
                stompClient.send("/app/chat.sendPrivateMessage", {}, JSON.stringify(chatMessage));
            } else {
                // Public message
                stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
            }

            // Message send hone ke baad turant display karein
            showMessage(chatMessage);
            messageInput.value = '';
        }
    };

    // Button click aur Enter key dono ke liye event listeners add kiye gaye hain
    sendButton.addEventListener('click', sendMessage);
    messageInput.addEventListener('keypress', (e) => {
        if (e.key === 'Enter') {
            sendMessage();
        }
    });

    // Display Message in Chat Log
    function showMessage(message) {
        const messageElement = document.createElement('div');
        messageElement.classList.add('message-bubble');
        messageElement.innerHTML = `<strong>${message.senderId}</strong>: ${message.content}`;
        chatLog.appendChild(messageElement);
        chatLog.scrollTop = chatLog.scrollHeight;
    }
});