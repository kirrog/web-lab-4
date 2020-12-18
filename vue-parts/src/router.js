import Vue from 'vue'
import VueRouter from "vue-router"
import LoginApp from "@/components/LoginApp";
import ErrorApp from "@/components/ErrorApp";
import MainApp from "@/components/MainApp";
import RegistrationApp from "@/components/RegistrationApp";

Vue.use(VueRouter);

export default new VueRouter({
    mode: "history",
    routes: [
        {
            path: "/Lab4/login",
            name: "auth-page",
            component: LoginApp
        },
        {
            path: "/Lab4/register",
            name: "reg-page",
            component: RegistrationApp
        },
        {
            path: "/Lab4/faces/app",
            name: "app-page",
            component: MainApp,
            beforeEnter: (to, from, next) => {
                if (localStorage.getItem("jwt")) next();
                else next({
                    name: "error-page",
                    params: {
                        errorCode: "401",
                        errorMessage: "You doesn't have access to this page. Please login before go to this page"
                    }
                });
            }
        },
        {
            path: "/Lab4/",
            name: "default-page",
            beforeEnter: (to, from, next) => {
                (localStorage.getItem("jwt") !== null) ? next({name: "app-page"}) : next({path: "/Lab4/login"});
            }
        },
        {
            path: "/Lab4/*",
            name: "error-page",
            component: ErrorApp,
            props: {
                default: true,
                errorCode: "404",
                errorMessage: "Page didn't found"
            }
        }
    ]
});