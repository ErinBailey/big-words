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

## Heroku DB info

To create the DB, I ran this command in the root folder of the project 

`heroku addons:create heroku-postgresql`

output:
```
Created postgresql-triangular-47028 as DATABASE_URL
```
Use `heroku addons:docs heroku-postgresql` to view documentation

List all environment/config variables 
`heroku config`

See all PostgreSQL databases provisioned by your application
`heroku pg:info`

Connect to the remote Heroku DB 
`heroku pg:psql`

## Lein REPL info

`lein deps` will install dependencies needed

`lein deps :tree` will show you where your deps have shifted from