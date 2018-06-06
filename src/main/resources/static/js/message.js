//=============================================================================
// Message
//=============================================================================
function Message()
{
    this.id = "";
    this.to = "";
    this.from = "";
    this.subject = "";
    this._receivedString = "";
    this.received = "";
    this.isRead = false;
    this.isViewed = false;
    this.isDeleted = false;
    this.importance = Mail.Importance.kNormal;
    this.body = "";
    this.messageSize = 0;
    this.attachments = 0;
}

function POPMessage()
{
    Message.call(this);
    this.messageID = Message.kNoId;
}

function IMAPMessage()
{
    Message.call(this);
    this.thrid = "";
    this.folderID = 0;
}

//-----------------------------------------------------------------------------
// Constants
//-----------------------------------------------------------------------------
Message.kNoId = -1;
