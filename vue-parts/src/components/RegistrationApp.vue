<template>
    <div id="registrationApp">
        <div class="pos-f-t">
            <div class="collapse show" id="navbarToggleExternalContent">
                <div class="bg-dark p-4">
                    <center>
                        <h5 class="text-white h4" id="app">Rogachev&Negrash presents</h5>
                        <span class="text-muted">Variant 2622</span><br>
                        <router-link to="/Lab4/login" class="btn btn-dark">Go</router-link>
                    </center>
                </div>
            </div>
            <nav class="navbar navbar-dark bg-dark d-flex justify-content-center">
                <center>
                    <button class="navbar-toggler" type="button" data-toggle="collapse"
                            data-target="#navbarToggleExternalContent" aria-controls="navbarToggleExternalContent"
                            aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>
                </center>
            </nav>
        </div>

        <div class="form-row">
            <div class="col-4"></div>
            <div class="col-4" id="form">
                <div class="alert alert-warning alert-dismissible fade" id="alert" role="alert">
                    <h6 id="register-status">Success</h6>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>

                <h3 class="display-4">
                    <center>Labwork 4: Registration</center>
                </h3>
                <br>


                <h6 class="lead">Email:</h6>
                <input v-model="login" id="email-text" placeholder="me@example.com" type="email"
                       class="form-control is-valid"
                       style="min-width:200px" required>

                <br>


                <h6 class="lead">Password:</h6>
                <input v-model="pass" id="password-text" placeholder="Your pass" class="form-control is-valid"
                       type="password"
                       style="min-width:200px" required>
                <input v-model="passR" id="password-r-text" placeholder="Repeat your pass" class="form-control is-valid"
                       type="password"
                       style="min-width:200px" required>

                <br>
                <button type="submit" id="register-btn" class="btn btn-lg btn-block btn-dark" v-on:click="greet">Register</button>

            </div>
            <div class="col-4"></div>
        </div>
    </div>
</template>

<script>

    import {sendDataRegistration} from "../scripts/index.js";

    export default {
        name: "Registration",
        data: function () {
            return {
                login: "",
                pass: "",
                passR: ""
            }
        },
        methods: {
            greet: function () {
                let btn = document.getElementById("register-btn");
                btn.setAttribute("disabled", "true");
                if (this.pass === this.passR) {
                    document.getElementById("password-text").classList.replace("is-invalid", "is-valid");
                    document.getElementById("password-r-text").classList.replace("is-invalid", "is-valid");
                    console.log("Right password repeating");
                    sendDataRegistration(this.login, this.pass);
                } else {
                    document.getElementById("password-text").classList.replace("is-valid", "is-invalid");
                    document.getElementById("password-r-text").classList.replace("is-valid", "is-invalid");
                    document.getElementById("register-status").innerText = "Wrong password repeating";
                    document.getElementById("alert").setAttribute("class", "alert alert-warning alert-dismissible fade show");
                    console.log("Wrong password repeating");
                    let btn = document.getElementById("register-btn");
                    btn.setAttribute("enabled", "true");
                }
            }
        }

    }
</script>
<style src="../styles/index.css">

</style>