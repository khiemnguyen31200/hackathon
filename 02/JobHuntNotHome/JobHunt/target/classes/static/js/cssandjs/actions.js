
let toggle = button => {
    let element = document.getElementById("job");
    let hidden = element.getAttribute("hidden");

    if (hidden) {
        element.removeAttribute("hidden");
        button.innerText = "Hide List Job";
    } else {
        element.setAttribute("hidden", "hidden");
        button.innerText = "View List Job";
    }
}


let toggleApllicant = button => {
    let element = document.getElementById("applicant");
    let hidden = element.getAttribute("hidden");

    if (hidden) {
        element.removeAttribute("hidden");
        button.innerText = "Hide List Applicant";
    } else {
        element.setAttribute("hidden", "hidden");
        button.innerText = "View List Applicant";
    }
}
