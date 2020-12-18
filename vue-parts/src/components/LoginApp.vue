<template>
    <div id="loginApp">
        <div class="pos-f-t">
            <div class="collapse show" id="navbarToggleExternalContent">
                <div class="bg-dark p-4">
                    <center>
                        <h5 class="text-white h4" id="app">Rogachev&Negrash presents</h5>
                        <span class="text-muted">Variant 2622</span>
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
                    <h6 id="login-status">Success</h6>
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>

                <h3 class="display-4">
                    <center>Labwork 4</center>
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
                <br>
                <button type="submit" id="login-btn" class="btn btn-lg btn-block btn-dark" v-on:click="greet">Log in</button>
                <br>
                <router-link to="/Lab4/register" class="btn btn-lg btn-block btn-light">Go register</router-link>
            </div>
            <div class="col-4"></div>
        </div>
    </div>
</template>

<script>

    import {sendDatas} from "../scripts/index.js";

    export default {
        name: "Index",
        data: function () {
            return {
                login: "",
                pass: ""
            }
        },
        methods: {
            greet: function () {
                let btn = document.getElementById("login-btn");
                btn.setAttribute("disabled", "true");
                sendDatas(this.login, this.pass);
                btn = document.getElementById("login-btn");
                btn.setAttribute("enabled", "true");
            }
        }

    }
</script>

<style src="../styles/index.css">
</style>