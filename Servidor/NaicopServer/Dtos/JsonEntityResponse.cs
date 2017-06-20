using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace NaicopServer.Dtos
{ 
    public class JsonResponse <T> where T:class
    { 
        public string status { get; set; }
        public T Entity { get; set; }
    }
}