/**
 * Handles the onClick event of the search button.
 * @param {Event} e 
 */
const onSearchUser = (e) => {
  e.preventDefault();
  

  let userName = document.forms[0].querySelector('input[name="username_search"]').value;
  const textArea = document.forms[0].querySelector('textarea[class="form-control"]');

  // Check if get all or get one
  let apiUrl = "http://localhost:8080/api/v1/user";
  if(userName.toString().length > 0) {
    apiUrl += "/" + userName;
  }


  const fetchOptions = {
    method: 'GET'
  };

  /**
   * Fetches the user from the API.
   */
  const fetchData = async () => {
    const response = await fetch(apiUrl, fetchOptions);
    const code = response.status;

    if (code === 200) {
      const user = await response.json();
      console.log("We got a response: ", user);
      textArea.value = JSON.stringify(user);
    }
    else {
      console.log("We got a response: ", code);
      textArea.value = "User not found";
    }
  }
  fetchData();
}
    
/**
 * 
 * @param {string} url  - The URL to fetch.
 * @param {string} data - The json information
 * @returns 
 */
const postData = async (url = '', data = {}) => {
  // Default options are marked with *

  const fetchOptions = {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    mode: 'cors',
    body: data
  };

  const response = await fetch(url, fetchOptions);
  return response.status;
}



/**
 * Handles the onClick event of the Add User button to send a POST request to the API.
 * @param {Event} e 
 */
const onAddUser = (e) => {
  e.preventDefault();

  const textArea = document.forms[0].querySelector('textarea[class="form-control"]')
  let data = textArea.value;
  // console.log("Data: ", data);


  const apiUrl = 'http://localhost:8080/api/v1/user/';


  /**
   * Fetches the user from the API.
   */
  const fetchData = async () => {
    const response = await postData(apiUrl, data);
    const code = response;

    if (code === 201) {
      // const user = await response.json();
      // console.log("We got a response: ", user);
      textArea.value = "Successfully added user";
    }
    else {
      console.log("We got a response: ", code);
      textArea.value = "Failed to add user";
    }
  }
  fetchData();
}