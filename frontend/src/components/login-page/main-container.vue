<template>
    <div class="login-page">

        <h2 color="#4d2279">Enter your SuperFrog/Spirit Director Username and Password</h2>
        <form>
            <div class="form-group">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" v-model="username"/>
            </div>
            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" v-model="password" />
            </div>
        </form>

        <div class="login-buttons">
            <button type="button" @click="loginAsSuperFrog()" class="btn btn-primary">
                Login as SuperFrog
            </button>
            <button type="button" @click="loginAsSpiritDirector()" class="btn btn-secondary">
                Login as Spirit Director
            </button>
        </div>
        <div v-if="errorMessage" class="error-message">{{ errorMessage }}</div>

        <router-link to="/">
        <button class="button is-primary home-button">Home</button>
      </router-link>
    </div>
</template>
<script>
import axios from 'axios';

export default {
    name: "LoginPage",
    data() {
        return {
            username: "",
            password: "",
            errorMessage: null,
        };
    },
    methods: {
        async loginAsSpiritDirector() {
            try {
                const loginData = {
                    username: this.username,
                    password: this.password
                };

                const response = await axios.post('http://localhost:8080/api/login', loginData, {
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    withCredentials: true
                });
                
                if (response.status === 200) {
                    alert('Login successful');
                    console.log(response.data);
                    this.$router.push('/spirit-director'); 
                } else {
                    this.errorMessage = 'Login failed';
                }
        
            } catch (error) {
                console.error(error);
                this.errorMessage = error.response.data.message;
            }
        },


        async loginAsSuperFrog() {
            try {
                // console.log(this.username);
                // console.log(this.password);
                // const basicAuth = 'Basic ' + btoa(this.username + ':' + this.password);
                // console.log(basicAuth);
                // const response = await axios.post('http://api.superfrogscheduler.xyz:8080/api/users/login', {}, {
                //     headers: {
                //         Authorization: basicAuth
                //     }
                // });
                // console.log(response.data);
                // const token = response.data.data.token;
                // const superfrogEmail = response.data.data.userInfo.username;
                // localStorage.setItem('token',token);
                // console.log(token);
                // localStorage.setItem('superfrogEmail', superfrogEmail);
                // console.log(superfrogEmail);
                // redirect to a new page after successful login
                this.$router.push('/superfrog');
            } catch (error) {
                console.error(error);
                this.errorMessage = error.response.data.message;
            }
        },
        goHome() {
            this.$router.push("/");
        },
    },

};
</script>

<style scoped>
    .login-page {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        height: 100vh;
        color: #4d2279;
    }

    form {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        background-color: #fff;
        padding: 20px;
        border-radius: 5px;
        box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2);
        width: 400px;
        margin-bottom: 20px;
        color: #4d2279;
    }

    .form-group {
        display: flex;
        flex-direction: row;
        align-items: center;
        justify-content: space-between;
        width: 100%;
        bottom: 500px;
        color: #4d2279;
    }

    label {
        font-weight: bold;
        color: black;
    }

    input[type="text"],
    input[type="password"] {
        padding: 10px;
        margin: 10px 0;
        border-radius: 5px;
        border: 1px solid #ccc;
        width: 70%;
        font-size: 16px;
        color: black;
    }

    button {
        font-size: 20px;
        padding: 10px;
        margin: 10px;
        border-radius: 5px;
        border: none;
        background-color: white;
        cursor: pointer;
        border-radius: 40px;
        display: inline-block;
        margin-bottom: 100px;
        color: #4d2279;
    }

    .btn-primary {
        background-color: white;
        width: 200px;
        color: #4d2279;
    }

    .btn-primary:hover,
    .btn-secondary:hover {
        opacity: 0.8;
    }

    .btn-secondary {
        margin-right: 0;
    }

    .login-buttons {
        display: flex;
        justify-content: center;
    }

    .home {
        display: flex;
        justify-content: center;
    }

    button.button.is-primary {
        width: 120px;
    }
</style>
