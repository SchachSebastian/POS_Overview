# Overview

Here are some more specific javascript methods and html tags listed.

### Summary

- [\<template>\</template>](#templatetemplate)
- [querySelector](#queryselector)
- [defer](#defer)
- [async](#async)
- [x-www-form-urlencoded](#x-www-form-urlencoded)

### \<template>\</template>

This tag is used to define a template that can be used in the document.

```html

<template id="template">
    <p>template</p>
</template>
```

```javascript
const template = document.getElementById("template");
const clone = template.content.cloneNode(true);
document.body.appendChild(clone);
```

### querySelector

This method is used to select the first element in the document or in a specific element matching the css selector.

Possible selectors are:

- `#id` to select an element with the specified id
- `.class` to select an element with the specified class
- `tag` to select an element with the specified tag

You can also chain selectors to select an specific tag with specific classes or ids.

```javascript
const element = document.querySelector("p");
```

```html
<p id="id" class="class">text</p> <!-- this will be selected -->
<p id="id2" class="class2">text</p>
```

### defer

This attribute is used to defer the execution of a script until the document has been parsed.

```html

<script defer src="script.js"></script>
```

### async

This attribute is used to execute a script asynchronously with the rest of the document.

```html

<script async src="script.js"></script>
```

### x-www-form-urlencoded

This is one of the content types for fetch requests.

```javascript
fetch(url, {
    method: "POST",
    headers: {
        "Content-Type": "application/x-www-form-urlencoded"
    },
    body: "name=value"
});
```

It will be sent in the body of the request but can be obtained in the server as if it was sent as a query parameter.
