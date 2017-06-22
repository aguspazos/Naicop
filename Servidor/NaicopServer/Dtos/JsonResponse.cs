using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Entitites;

namespace NaicopServer.Dtos
{ 
    public class JsonResponse <T> where T:class
    {
        public string Status { get; set; }
        public T Entity { get; set; }

        public JsonResponse(string status, T Entity)
        {
            this.Status = status;
            this.Entity = Entity;
        }

        public JsonResponse()
        {
        }
    }
}