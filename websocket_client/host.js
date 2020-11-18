const express = require('express')
const app = express()

if (process.argv.length < 3) {
  console.log("Please specify a port number")
  return
}
const port = process.argv[2]

app.use('/', express.static('./static'))

app.listen(port, () => {
  console.log(`Example app listening at http://localhost:${port}`)
})
