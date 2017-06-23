using Entitites;
using Entitites.Exceptions;
using Repository;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Utils;

namespace Services.Implementations
{
    public class ClientUserService : IClientUserService
    {
        private IUnitOfWork unitOfWork;

        public ClientUserService()
        {
            this.unitOfWork = new UnitOfWork();
        }
        public ClientUserService(IUnitOfWork unitOfWork)
        {
            this.unitOfWork = unitOfWork;
        }

        public ClientUser Login(string email, string password)
        {
            IEnumerable<ClientUser> clientUsers = unitOfWork.ClientUserRepository.Get((u => u.Email == email && u.Password.Equals(password)));

            if (clientUsers.Count() > 0)
            {
                return clientUsers.First();
            }
            return null;
        }

        public ClientUser CreateClientUser(ClientUser clientUser,string image,string imageName)
        {
            IEnumerable<ClientUser> existingUsers = unitOfWork.ClientUserRepository.Get((u => u.Email == clientUser.Email));
            if (existingUsers.Count() > 0)
            {
                throw new ClientUserException("Ya existe un usuario con ese email");
            }
            else
            {
                clientUser.Role = Roles.CLIENT_USER;
                string imagePath = ImageHelper.saveImage(image, imageName);
                clientUser.ImageUrl = imagePath;
                unitOfWork.ClientUserRepository.Insert(clientUser);
                unitOfWork.Save();
                return clientUser;
            }

        }

    }
}
