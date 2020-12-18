<template>

    <div>
        <h1 class="display-1">Error {{errorCode}}</h1>
        <h1>
            <center>User didn't login</center>
        </h1>
        <p class="lead"/>
        <center>{{errorMessage}}</center>
        <router-link to="/Lab4/login" class="btn btn-lg btn-block btn-dark" >Return to login</router-link>
    </div>

</template>

<script>
    export default {
        name: "ErrorApp",
        props:['errorCode', 'errorMessage']
    }
</script>

<style>
    #back {
        background: linear-gradient(271deg, #ffffff, #1b0bf2, #f20d0b);
        background-size: 600% 600%;

        -webkit-animation: AnimationName 30s ease infinite;
        -moz-animation: AnimationName 30s ease infinite;
        -o-animation: AnimationName 30s ease infinite;
        animation: AnimationName 30s ease infinite;
    }

    @-webkit-keyframes AnimationName {
        0% {
            background-position: 0% 50%
        }
        50% {
            background-position: 100% 50%
        }
        100% {
            background-position: 0% 50%
        }
    }

    @-moz-keyframes AnimationName {
        0% {
            background-position: 0% 50%
        }
        50% {
            background-position: 100% 50%
        }
        100% {
            background-position: 0% 50%
        }
    }

    @-o-keyframes AnimationName {
        0% {
            background-position: 0% 50%
        }
        50% {
            background-position: 100% 50%
        }
        100% {
            background-position: 0% 50%
        }
    }

    @keyframes AnimationName {
        0% {
            background-position: 0% 50%
        }
        50% {
            background-position: 100% 50%
        }
        100% {
            background-position: 0% 50%
        }
    }
</style>