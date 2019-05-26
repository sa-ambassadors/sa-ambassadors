(function() {

    "use script";

    console.log("test");

    document.querySelector("#save").addEventListener("click", saveInternProfile);

    let username = document.querySelector("#username").innerHTML;

    //Intern profile fields
    let firstName = null;
    let lastName = null;
    let major = null;
    let minor = null;
    let field1 = 0;
    let field2 = 0;
    let field3 = 0;
    let transcriptLink = null;
    let essayLink = null;
    let saHighSchool = false;
    let firstToCollege = false;

    //Employer profile fields
    let empName = null;
    let supervisor = null;
    let email = null;
    let industry = null;
    let location = null;
    let websiteURL = null;
    let description = null;

    function saveInternProfile(event) {
        event.preventDefault();
        setInternVars();
        console.log(firstName);
        console.log("test");
        fetch("/api/v1/" + username + "/intern/profile/edit", {
            method: 'PATCH',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                "first_name": firstName,
                "last_name": lastName,
                "major": major,
                "minor": minor,
                "field_1": field1,
                "field_2": field2,
                "field_3": field3,
                "transcript_link": transcriptLink,
                "essay_link": essayLink,
                "sa_highschool": saHighSchool,
                "first_to_college": firstToCollege
            })
        })
    }

    function saveEmployerProfile(event) {
        event.preventDefault();
        setEmployerVars();
        fetch("/api/v1/" + username + "/employer/profile/edit", {
            method: 'PATCH',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                "name": empName,
                "supervisor": supervisor,
                "email": email,
                "industry": industry,
                "location": location,
                "websiteUrl": websiteURL,
                "description": description
            })
        })

    }

    function setInternVars() {
        firstName = document.querySelector("#firstName").innerHTML;
        lastName = document.querySelector("#lastName").innerHTML;
        major = document.querySelector("#major").innerHTML;
        minor = document.querySelector("#minor").innerHTML;
        field1 = document.querySelector("#field_1").innerHTML;
        field2 = document.querySelector("#field_2").innerHTML;
        field3 = document.querySelector("#field_3").innerHTML;
        transcriptLink = document.querySelector("#transcript_link").innerHTML;
        essayLink = document.querySelector("#essay_link").innerHTML;
        saHighSchool = document.querySelector("#sa_highschool").innerHTML;
        document.querySelector("#sa_college").innerHTML;
        firstToCollege = document.querySelector("#first_to_college").innerHTML;
    }

    function setEmployerVars() {
    }

})();