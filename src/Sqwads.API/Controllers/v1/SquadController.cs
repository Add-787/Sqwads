using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Sqwads.Application.Squads.CreateSquad;

namespace Sqwads.API.Controllers.v1;

[ApiVersion("1.0")]
public class SquadController : BaseApiController
{
    [HttpPost]
    public async Task<ActionResult<int>> Create(CreateSquadCommand command, CancellationToken token) => Ok(await Mediator.Send(command, token));
}
