using System;
using System.Runtime.Serialization;

namespace NaicopServer.Controllers
{
    [Serializable]
    internal class SecurityClientException : Exception
    {
        public SecurityClientException()
        {
        }

        public SecurityClientException(string message) : base(message)
        {
        }

        public SecurityClientException(string message, Exception innerException) : base(message, innerException)
        {
        }

        protected SecurityClientException(SerializationInfo info, StreamingContext context) : base(info, context)
        {
        }
    }
}