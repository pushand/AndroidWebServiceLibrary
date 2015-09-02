# AndroidWebServiceLibrary

This library make http get/post request calls and handles only JSON response.

Usage
-----


	1.	Your API class can extend BaseApi for you additional logic or directly create object of BaseApi class

	    public class MyApi extends BaseApi{
                public MyApi() {
                        super(BaseApi.REQUEST_TYPE_POST,"http://your-api.com");

                        //not compulsory. set content type url encoded (default) or json
                        setContentType(APP_URL_ENCODED);

                        // add url param to get or post request
                        addUrlParams("email", "your email");
                        addUrlParams("password", "your password");

                        //send params to post request
                        addParams("your-key","your-value")

                        //send json body to post request
                        setJsonPostBody(Object yourClassThatNeedsToBeConvertedToJSON)
                    }
         }

	2.	Make web service call
	    ⁃   //Response and Error are your bean (any java class) that will be mapped to json response received from server getInputStream
	        //and getErrorStream respectively.

	    ⁃   WebServiceHelper.makeWebServiceCall(new MyApi(), new WebServiceHelper.Callback<Response, Error>() {
	                    //will be called if response status code is 200
                        @Override
                        public void response(Response response) {

                        }
                        // will be called in case of IOException where response status code != 200
                       //  @param errorMessage is IOException message
                        @Override
                        public void error(Error error, String errorMessage, int statusCode) {

                        }
                    });

          ⁃   //If you wish to receive raw Response pass String as Generic class
              // WebServiceHelper.Callback<String, String>


