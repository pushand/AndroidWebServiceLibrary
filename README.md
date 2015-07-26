# AndroidWebServiceLibrary

This library make http get/post request calls and handles only JSON response.

Usage
-----


	1.	Your API class should extend BaseApi
	    ⁃	MyApi extends BaseApi
	    ⁃	Call super(BaseApi.REQEST_TYPE , “url/to/your/api” ) in your default constructor.
	    ⁃	Additionally add params for your get/post request call if required by calling addParam(“key”,”value”) method.
	    ⁃	example :
                public MyApi() {
                        super(BaseApi.REQUEST_TYPE_POST,"http://your-api.com");
                        addParams("email", "your email");
                        addParams("password", "your password");
                    }

	2.	Make web service call
	    ⁃   //Hello and Error are your bean that will be mapped to json response received from server
	    ⁃   WebServiceHelper.makeWebServiceCall(new MyApi(), new WebServiceHelper.Callback<Hello, Error>() {
                        @Override
                        public void response(Hello hello) {

                        }

                        @Override
                        public void error(Error error, String errorMessage, int statusCode) {

                        }
                    });


