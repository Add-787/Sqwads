using MediatR;

namespace Sqwads.Application.Squads.CreateSquad;

public class CreateSquadCommand : IRequest<int>
{
    public string Name { get; init;  }
}

public class CreateSquadCommandHandler : IRequestHandler<CreateSquadCommand, int>
{
    public Task<int> Handle(CreateSquadCommand request, CancellationToken cancellationToken)
    {
        throw new NotImplementedException();
    }

}
