import * as React from 'react'

type MessageComponentProps = {
    sender: string,
    text: string,
    createdAt: Date,
}

/*
 A component is a function:
 - from a model, also called the "props" (properties)
 - into a view, i.e, a tree of virtual DOM elements
 */
export function MessageComponent({
    sender, 
    text, 
    createdAt
}: MessageComponentProps): React.ReactElement {
    const senderLine = `${sender} at ${createdAt} said`
    //return React.createElement('div', {className: "message"}, 
    //    React.createElement('p', {}, senderLine),
    //    React.createElement('p', {}, text)
    //)
    return <div className="message">
        <p>{senderLine}</p>
        <p>{text}</p> 
        <input type="text" />
    </div>
}
