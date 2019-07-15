# big-words 

## To run the REPL

 Note: must have lein/java/clojure installed
  
  `lein repl` 

-------

## To run the app locally

In the REPL 


`(run-jetty app {:port 3000})`

and visit localhost:3000 or 0.0.0.0:3000

-------

## To deploy the app to heroku

``` 
git add .
git commit -m "message"
git push heroku master
```

Note: use `heroku login` to authenticate the deploy, do not authenticate heroku through git

-------