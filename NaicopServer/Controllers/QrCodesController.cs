using Entitites;
using Services;
using Services.Implementations;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Http;

namespace NaicopServer.Controllers
{
    public class QrCodesController : ApiController
    {
        private IQrCodeService qrCodeService;

        public QrCodesController()
        {
            this.qrCodeService = new QrCodeService();
        }

        // GET api/<controller>/id
        public string Get(int id)
        {
            return "value";
        }

        // POST api/<controller>
        [Route("api/qrCodes")]
        [HttpPost]
        public IHttpActionResult Post([FromBody]QrCode qrCode)
        {
            QrCode newQrCode= qrCodeService.CreateQRCode(qrCode);
            return Ok(qrCode);

        }

        // PUT api/<controller>/id
        public void Put(int id, [FromBody]string value)
        {
        }

        // DELETE api/<controller>/id
        public void Delete(int id)
        {
        }
    }
}